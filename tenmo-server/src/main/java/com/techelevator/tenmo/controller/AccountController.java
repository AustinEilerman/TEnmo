package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
// @PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    //Endpoints for balance and pulling all users lists.
    @RequestMapping(path = "/balance/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable("id") int userId) {
        BigDecimal balance = accountDao.getBalance(userId);
        return balance;
    }

    @RequestMapping(path = "/listusers", method = RequestMethod.GET)
    public List<User> listusers() {
        List<User> users = userDao.findAll();
        return users;
    }

}
