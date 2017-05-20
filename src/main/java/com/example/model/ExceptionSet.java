package com.example.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ExceptionSet")
public class ExceptionSet {
	
	@Id
	private String id;
	private ArrayList<Subject> set;
	
	public ExceptionSet(){
		set = new ArrayList<Subject>();
	}
	
	public ExceptionSet(ArrayList<Subject> set){
		this.set = set;
	}
	
	public boolean contain(Subject s){
		return this.set.contains(s);
	}
	
	public void addSubject(Subject s){
		this.set.add(s);
	}
	
	public Subject removeSubject(Subject s){
		return this.set.remove(this.set.indexOf(s));
	}

}
