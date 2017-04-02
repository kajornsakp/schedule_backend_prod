package com.example.controller;

import com.example.config.MongoConfig;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kajornsak on 2/13/2017 AD.
 */
@RestController
@RequestMapping("/auth")
public class MainController {

    ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
    MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
    //DataHandler dataHandler = new DataHandler();

    @RequestMapping(value = "/listUsers",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUser() {
    	
        return mongoOperation.findAll(User.class);
    }
    
    @RequestMapping(value = "/login",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public List<User> login(@RequestBody User req) {
        List<User> users = mongoOperation.find(new Query(Criteria.where("username").is(req.getName())), User.class);
        return users;
    }
    
    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public User registerUser(@RequestBody User req) {
        mongoOperation.save(req);
        return req;
    }
    
    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> deleteAll(){
    	mongoOperation.dropCollection("User");
    	return mongoOperation.findAll(User.class);
    }
    
    
    
    

}

