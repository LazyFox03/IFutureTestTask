package com.foxdatabase.ifuturetesttask.service;

import com.foxdatabase.ifuturetesttask.model.Account;
import com.foxdatabase.ifuturetesttask.repository.AccountRepositoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepositoryDao accountRepositoryDao;
    private final Map<Integer, Account> accountsHash = new ConcurrentHashMap<>();

    @Autowired
    public AccountServiceImpl(AccountRepositoryDao accountRepositoryDao) {
        this.accountRepositoryDao = accountRepositoryDao;
    }

    @Override
    public synchronized Long getAmount(Integer id) {
        if (!accountsHash.containsKey(id)) {
            return 0L;
        } else {
            return accountsHash.get(id).getAmount();
        }
    }

    @Override
    public synchronized void addAmount(Integer id, Long value) {
        if (!accountsHash.containsKey(id)) {
            accountsHash.put(id, new Account(id, value));
            accountRepositoryDao.save(new Account(id, value));
        } else {
            Account account = accountsHash.get(id);
            account.addAmount(value);
            accountRepositoryDao.save(account);
        }
    }
}
