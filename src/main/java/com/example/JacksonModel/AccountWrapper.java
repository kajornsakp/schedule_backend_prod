package com.example.JacksonModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountWrapper {
	String username;
	String password;
	
	public AccountWrapper(@JsonProperty("name") String username, @JsonProperty("password") String password){
		this.username = username;
		this.password = password;
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
