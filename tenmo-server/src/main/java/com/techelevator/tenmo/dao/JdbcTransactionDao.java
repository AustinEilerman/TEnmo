package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcTransactionDao implements TransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    public JdbcTransactionDao(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;

    }

    //SQL injection to pull information needed to send transfer to another party.
    // Uses addToBalance and subtract from balance from AccountDAO

    @Override
    public String sendTransfer(int transferFrom, int transferTo, BigDecimal amount) {
        final String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(2,2,?,?,?)";
        jdbcTemplate.update(sql, transferFrom, transferTo, amount);
        accountDao.addToBalance(amount, transferTo);
        accountDao.subtractFromBalance(amount, transferFrom);
        return "Your transaction is complete";
    }

    //SQL injection that pulls from our database and allows the user to request money from another user
    @Override
    public String transferFromUser(int transferFrom, int transferTo, BigDecimal amount) {
        final String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (1,1,?,?,?)";
        jdbcTemplate.update(sql, transferFrom, transferTo, amount);
        accountDao.subtractFromBalance(amount, transferFrom);
        accountDao.addToBalance(amount, transferTo);
        return "Your transaction is complete";
    }

//SQL injection using UserID to get all transactions under their UserId.

    @Override
    public List<Transaction> getAllTransactions(int userId) {
        List<Transaction> transactions = new ArrayList<>();

        final String sql = "SELECT transfer.transfer_id,transfer_type.transfer_type_id, " +
                "tenmo_user.username AS username_from, tenmo_two.username AS username_to, transfer.amount, "+
        "transfer.account_to, transfer.account_from, transfer_type.transfer_type_desc, transfer_status_desc "+
                "FROM transfer " +
                "JOIN account ON transfer.account_from = account.account_id " +
                "JOIN account AS account_two ON transfer.account_to = account_two.account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "JOIN tenmo_user AS tenmo_two ON account_two.user_id = tenmo_two.user_id " +
                "JOIN transfer_type ON transfer.transfer_type_id  =transfer_type.transfer_type_id "+
                "JOIN transfer_status ON  transfer.transfer_status_id = transfer_status. transfer_status_id " +
                "WHERE tenmo_user.user_id = ? OR tenmo_two.user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next()) {
            transactions.add(mapRowToTransaction(results));
        }
        return transactions;
    }
//SQL injection to get transaction details by transactionID
    @Override
    public Transaction getTransactionById(int transactionId) {
        Transaction transactions = new Transaction();
        final String sql = "SELECT transfer.transfer_id,transfer_type.transfer_type_id, " +
                "tenmo_user.username AS username_from, tenmo_two.username AS username_to, transfer.amount, " +
                "transfer.account_to, transfer.account_from, transfer_type.transfer_type_desc, transfer_status_desc " +
                "FROM transfer " +
                "JOIN account ON transfer.account_from = account.account_id " +
                "JOIN account AS account_two ON transfer.account_to = account_two.account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "JOIN tenmo_user AS tenmo_two ON account_two.user_id = tenmo_two.user_id " +
                "JOIN transfer_type ON transfer.transfer_type_id = transfer_type.transfer_type_id " +
                "JOIN transfer_status ON  transfer.transfer_status_id = transfer_status. transfer_status_id " +
                "WHERE transfer.transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transactionId);
        if (results.next()) {
            transactions = mapRowToTransaction(results);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getPending(int accountId) {
        return null;
    }

    @Override
    public String updateTransactionRequest(Transaction transaction, int statusId) {
        return null;
    }
  //Maps for all fields used to get transfers by UserId and TransactionID
    private Transaction mapRowToTransaction(SqlRowSet result) {
        Transaction transaction = new Transaction();
        transaction.setTransferId(result.getInt("transfer_id"));
        transaction.setTransferTypeId(result.getInt("transfer_type_id"));

        transaction.setAccountFrom(result.getInt("account_from"));
        transaction.setAccountTo(result.getInt("account_to"));

        transaction.setAmount(result.getBigDecimal("amount"));

        transaction.setUsernameFrom(result.getString("username_from"));
        transaction.setUsernameTo(result.getString("username_to"));

        transaction.setTransferStatus(result.getString("transfer_status_desc"));
        transaction.setTransferType(result.getString("transfer_type_desc"));

        return transaction;
    }

}
