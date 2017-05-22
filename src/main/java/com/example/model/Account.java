package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;



@Document(collection = "User")
public class Account {
    @Id
    String id;
    
    String username;
    String password;
    String role;
    String firstName;
    String lastName;
    String email;
    

    Lecturer lecturer;

    public Account() {
    	
    }
    
    public Account(@JsonProperty("username") String username, @JsonProperty("password") String password, 
    		@JsonProperty("role") String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
        return this.username;
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

	public Lecturer getLecturer() {
		return lecturer;
	}

	public void setLecturer(Lecturer lecturer) {
		this.lecturer = lecturer;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
	
	@Override
	public String toString() {
		return this.username + " : " + this.password;
	}
    
    
}
