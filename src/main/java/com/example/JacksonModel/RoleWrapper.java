package com.example.JacksonModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleWrapper {
	private String accountName;
	private String role;
	
	
	public RoleWrapper(@JsonProperty("username")String name,@JsonProperty("role") String role){
		this.accountName = name;
		this.role = role;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
}
