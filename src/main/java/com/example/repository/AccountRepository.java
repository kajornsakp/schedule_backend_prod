package com.example.repository;

import com.example.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
	public Account findByUsername(String username);
	public Account findByUsernameAndPassword(String username, String password);
	
}