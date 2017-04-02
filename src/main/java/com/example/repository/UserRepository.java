package com.example.repository;

import com.example.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by kajornsak on 2/13/2017 AD.
 */
public interface UserRepository extends MongoRepository<User,String> {
    public User findByName(String name);

}
