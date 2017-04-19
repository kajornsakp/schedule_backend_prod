package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kajornsak on 2/13/2017 AD.
 */

@Document(collection = "User")
public class User {
    @Id
    String id;
    String name;
    String password;
    String role;

    public User(@JsonProperty("username") String name, @JsonProperty("password") String password, 
    		@JsonProperty("role") String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
