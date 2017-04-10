package com.example.controller;

import com.example.DataHandler.DataHandler;

import com.example.model.User;


import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping(value = "/listUsers",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUser() {
        return DataHandler.getAllUsers();
    }
    
    @RequestMapping(value = "/login",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public List<User> login(@RequestBody User req) {
        return DataHandler.findByUser(req.getName());
    }
    
    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public boolean registerUser(@RequestBody User req) {
        return DataHandler.addUser(req);
    }
    
    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> deleteAll(){
    	return DataHandler.deleteAllUsers();
    }
    
    
    
    

}

