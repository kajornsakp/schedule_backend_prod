package com.example.JacksonModel;

import java.util.List;

import com.example.model.Lecturer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectWrapper {
	private String name;
	private List<String> lecturerList;
	private int expectedStudent;
	private int priority;
	
	public SubjectWrapper(){
		
	}
	
	public SubjectWrapper(@JsonProperty("name") String name, @JsonProperty("lecturerList") List<String> lecturerList, 
			@JsonProperty("priority")int priority, @JsonProperty("expectedStudent") int expectedStudent){
		this.name = name;
		this.lecturerList = lecturerList;
		this.priority = priority;
		this.expectedStudent = expectedStudent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getLecturerList() {
		return lecturerList;
	}
	public void setLecturerList(List<String> lecturerList) {
		this.lecturerList = lecturerList;
	}
	public int getExpectedStudent() {
		return expectedStudent;
	}
	public void setExpectedStudent(int expectedStudent) {
		this.expectedStudent = expectedStudent;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public String toString(){
		return this.getName();
	}
	
	
}
