package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    private TransactionDao transactionDao;

    public TransactionController(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    //Setting up endpoint for the transfer (transactions model)
    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.POST)
    public String sendTransferReq(@RequestBody Transaction transaction) {
        String str = transactionDao.sendTransfer(transaction.getAccountFrom(), transaction.getAccountTo(), transaction.getAmount());
        return str;
    }

    //Setting up endpoint to get all transactions from the users account
    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public List<Transaction> getAllTransactions(@PathVariable("id") int userId) { //
        List<Transaction> transaction = transactionDao.getAllTransactions(userId);
        return transaction;
    }

    //Setting up endpoint for searching the transfer (transactions model)
    @RequestMapping(path = "/search/{id}", method = RequestMethod.GET)
    public Transaction getTransactionById(@PathVariable("id") int transactionId) {
        Transaction str = transactionDao.getTransactionById(transactionId);
        return str;
    }

}
