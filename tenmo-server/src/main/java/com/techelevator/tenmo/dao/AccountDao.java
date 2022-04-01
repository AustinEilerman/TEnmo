package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    //Account findAccountByUserId(int userId);

    public Account getAccount(long accountId);

    BigDecimal getBalance(int userId);

    BigDecimal addToBalance(BigDecimal amountAdd, int accountId);

    BigDecimal subtractFromBalance(BigDecimal amountSub, int accountId);
}
