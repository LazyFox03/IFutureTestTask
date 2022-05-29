package com.foxdatabase.ifuturetesttask.repository;

import com.foxdatabase.ifuturetesttask.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepositoryDao extends CrudRepository<Account, Integer> {

}
