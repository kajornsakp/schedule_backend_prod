package com.example.model;

import java.util.ArrayList;

public class ExceptionSet {
	private ArrayList<Subject> set;
	
	public ExceptionSet(){
		set = new ArrayList<Subject>();
	}
	
	public ExceptionSet(ArrayList<Subject> set){
		this.set = set;
	}
	
	public boolean have(Subject s){
		return this.set.contains(s);
	}
	
	public void addSubject(Subject s){
		this.set.add(s);
	}
	
	public Subject removeSubject(Subject s){
		return this.set.remove(this.set.indexOf(s));
	}

}
