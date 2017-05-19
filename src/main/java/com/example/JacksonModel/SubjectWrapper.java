package com.example.JacksonModel;

import java.util.List;

import com.example.model.Lecturer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectWrapper {
	private String name;
	private List<String> lecturerList;
	private int expectedStudent;
	private int priority;
	private String id;
	
	public SubjectWrapper(){
		
	}
	
	public SubjectWrapper(@JsonProperty("id") String id) {
		this.id = id;
	}
	
	
}
