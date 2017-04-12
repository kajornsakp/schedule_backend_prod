package com.example.repository;

import com.example.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by kajornsak on 2/13/2017 AD.
 */
public interface UserRepository extends MongoRepository<Account,String> {
    public Account findByName(String name);

}
