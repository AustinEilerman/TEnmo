package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class JdbcAccountDao implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//gets balance with user id from an ccount

    @Override
    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance " +
                "FROM account " +
                "WHERE user_id = ?;";
        SqlRowSet results = null;
        BigDecimal balance = null;
        try {
            //should we queryForObject instead since one field
            results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                balance = results.getBigDecimal("balance");
            }
        } catch (DataAccessException x) {
            System.out.println("Error: Unable to access!");
        }
        return balance;
    }

//this method gets the account information using the account id

    @Override
    public Account getAccount(long accountId) {
        Account account = null;
        final String sql = "SELECT account_id, user_id, balance " +
                "FROM account " +
                "WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

//this method gets the account by user ID

    public Account findAccountByUserId(int userId) {
        Account account = null;
        final String sql = "SELECT account_id, user_id, balance " +
                "FROM account " +
                "WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        if (result.next()) {
            account = mapRowToAccount(result);
        }
        return account;
    }

//The method adds to Balance and returns new updated balance.

    @Override
    public BigDecimal addToBalance(BigDecimal amountAdd, int accountId) {
        final String sql = "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE account_id = ?";
        jdbcTemplate.update(sql, amountAdd, accountId);
        return this.getAccount(accountId).getBalance();
    }

//Subtracts balance and returns new balance.

    @Override
    public BigDecimal subtractFromBalance(BigDecimal amountSub, int accountId) {
        final String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE account_id = ?";
        jdbcTemplate.update(sql, amountSub, accountId);
        return this.getAccount(accountId).getBalance();
    }
//Simple mapper.
    private Account mapRowToAccount(SqlRowSet result) {
        Account account = new Account();
        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));
        account.setBalance(result.getBigDecimal("balance"));
        return account;
    }

}