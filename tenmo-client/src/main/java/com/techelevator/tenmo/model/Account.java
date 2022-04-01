package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.math.BigDecimal;
//TODO created Account and Account Services with setters and getters in Account
public class Account {

    private int accountId;
    @JsonProperty("id")
    private int userId;
    private BigDecimal balance;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
