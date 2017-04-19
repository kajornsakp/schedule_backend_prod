package com.example.controller;

import com.example.DataHandler.DataHandler;
import com.example.JacksonModel.AccountWrapper;
import com.example.JacksonModel.RoleWrapper;
import com.example.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping(value = "/listUsers",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> getUser() {
        return DataHandler.getAllUsers();
    }
    
    @RequestMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public Account login(@RequestBody Account req) {
    	System.out.println("authen completed returning Account object!!!");
        return DataHandler.findByUserandPassword(req.getUsername(), req.getPassword());
    }
    
    @RequestMapping(value = "/register",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public  ResponseEntity<Object> registerUser(@RequestBody AccountWrapper req) {
    	Account newAcc = new Account(req.getUsername(), req.getPassword(), "USER");
    	try {
            DataHandler.addUser(newAcc);
            return ResponseEntity.ok(newAcc);
        } catch (IllegalAccessException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }
    
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> deleteAll(){
    	return DataHandler.deleteAllUsers();
    }
    
    @RequestMapping(value = "/changeRole", method = RequestMethod.OPTIONS, consumes = "application/json")
    public Account changeRole(@RequestBody RoleWrapper wrapper){
    	return DataHandler.changeAccountRole(wrapper.getAccountName(), wrapper.getRole());
    }
    
}

