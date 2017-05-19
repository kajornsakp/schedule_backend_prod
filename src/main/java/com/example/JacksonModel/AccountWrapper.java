package com.example.JacksonModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountWrapper {
	String username;
	String password;
	String id;
	String role;
	
	public AccountWrapper() {
		
	}
	
	public AccountWrapper(@JsonProperty("role") String role ,@JsonProperty("id") String id, @JsonProperty("name") String username, @JsonProperty("password") String password){
		this.username = username;
		this.password = password;
		this.id = id;
	}
	
	
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
