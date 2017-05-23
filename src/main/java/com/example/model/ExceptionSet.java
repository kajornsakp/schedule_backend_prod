package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "ExceptionSet")
public class ExceptionSet {
	
	@Id
	private String id;

	private String setName;

	public ExceptionSet(@JsonProperty("setName") String setName){
		this.setName = setName;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		ExceptionSet setB = (ExceptionSet) obj;
		return this.setName.equals(setB.getSetName());
	}
}
