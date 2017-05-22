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
    private String name;
    private String info;
    
    @Transient
    private ArrayList<Subject> subjects;
    
    public Lecturer(){
    	
    }

    public Lecturer(@JsonProperty("id") String id){
    	this.id = id;
    	this.info = "";
    }
    
    
    public Lecturer(String name, String info){
        this.name = name;
        this.info = info;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		Lecturer lecturer = (Lecturer) obj; 
		if (lecturer.getId().equals(this.getId()))
			return true;
		return false;
	}
	
	

	
    
    
    


}
