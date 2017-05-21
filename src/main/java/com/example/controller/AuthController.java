package com.example.controller;


import com.example.model.Account;
import com.example.model.Lecturer;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthController implements Controllers<Account>{
	
	@Autowired
	private AccountRepository repository;
	

    @RequestMapping(value = "/listUsers",method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Account> listAll() {
    	return repository.findAll();
    }
    
    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public ResponseEntity<Object> login(@RequestBody Account req) {
    	Account result = repository.findByUsernameAndPassword(req.getUsername(), req.getPassword());
    	if (result == null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found!!!");
    	return ResponseEntity.ok(result);
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public ResponseEntity<Object> create(@RequestBody Account req) {
    	Account account = repository.findByUsername(req.getUsername());
    	if (account != null)
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this username has already existed!!!");
    	Account newAcc = new Account(req.getUsername(), req.getPassword(), "USER");
    	newAcc.setLecturer(new Lecturer(newAcc.getFirstName() + " " + newAcc.getLastName()));
    	repository.save(newAcc);
    	return ResponseEntity.ok(newAcc);

    }
    
    @RequestMapping(method = RequestMethod.DELETE, consumes="application/json")
    public void delete(@RequestBody Account req) {
    	repository.delete(req.getId());
    }
    
    @RequestMapping(value = "/all", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
    public void deleteAll(){
    	repository.deleteAll();
    }
    
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public Account update(@RequestBody Account acc){
    	return repository.save(acc);    	
    }

}

