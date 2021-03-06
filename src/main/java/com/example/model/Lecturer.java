package com.example.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "Lecturer")
public class Lecturer {

	@Id
	private String id;
    private String lecName;
    private String info;
    
    private ArrayList<String> subjects;
    
    public Lecturer(){
    	
    }
    
    
    public Lecturer(@JsonProperty String lecName) {
    	this.lecName = lecName;
    }

	public String getLecName() {
		return lecName;
	}

	public void setLecName(String lecName) {
		this.lecName = lecName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ArrayList<String> getSubjects() {
		return subjects;
	}

	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("check contain");
		String lecturer = (String) obj;
		if (lecturer.equals(this.getId()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return this.getLecName();
	}
	
	

	
    
    
    


}
