package service;

import dao.AccountDao;
import entity.Account;

public class AccountService {
    private final AccountDao accountDao = new AccountDao();

    public Account login(String username, String password) {
        return accountDao.login(username, password);
    }
}
