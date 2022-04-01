package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {
    //Few methods were not completed (optional)

    List<Transaction> getAllTransactions(int userId);

    Transaction getTransactionById(int transactionId);

    String sendTransfer(int transferFrom, int transferTo, BigDecimal amount);

    String transferFromUser(int transferFrom, int transferTo, BigDecimal amount);

    List<Transaction> getPending(int accountId);

    String updateTransactionRequest(Transaction transaction, int statusId);
}
