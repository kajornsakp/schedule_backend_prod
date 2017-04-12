package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;



@Document(collection = "User")
public class Account {
    @Id
    String id;
    String username;
    String password;
    String role;

    public Account(@JsonProperty("username") String username, @JsonProperty("password") String password, 
    		@JsonProperty("role") String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public void setPassword(String password){
    	this.password = password;
    }
    
    public String getPassword(){
    	return this.password;
    }
    
    public String getRole(){
    	return this.role;
    }
    
    public void setRole(String role){
    	this.role = role;
    }
    
    
}
