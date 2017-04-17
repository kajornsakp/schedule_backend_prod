package com.example.controller;

import com.example.DataHandler.DataHandler;
import com.example.JacksonModel.AccountWrapper;
import com.example.JacksonModel.RoleWrapper;
import com.example.model.Account;


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
    public List<Account> getUser() {
        return DataHandler.getAllUsers();
    }
    
    @RequestMapping(value = "/login",method = RequestMethod.OPTIONS, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public Account login(@RequestBody Account req) {
    	System.out.println("authen completed returning Account object!!!");
        return DataHandler.findByUser(req.getUsername());
    }
    
    @RequestMapping(value = "/register",method = RequestMethod.OPTIONS, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public boolean registerUser(@RequestBody AccountWrapper req) {
    	Account newAcc = new Account(req.getUsername(), req.getPassword(), "ADMIN");
        return DataHandler.addUser(newAcc);
    }
    
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> deleteAll(){
    	return DataHandler.deleteAllUsers();
    }
    
    @RequestMapping(value = "/changeRole", method = RequestMethod.OPTIONS, consumes = "application/json")
    public Account changeRole(@RequestBody RoleWrapper wrapper){
    	return DataHandler.changeAccountRole(wrapper.getAccountName(), wrapper.getRole());
    }
    
}

