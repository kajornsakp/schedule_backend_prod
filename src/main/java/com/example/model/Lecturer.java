package com.example.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Lecturer")
public class Lecturer {

	@Id
	private long id;
    private String name;
    private String info;
    private ArrayList<Subject> subjects;
    
    public Lecturer(){
    	
    }

    public Lecturer(String name){
    	this.name = name;
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
    
    
    
    
    


}
