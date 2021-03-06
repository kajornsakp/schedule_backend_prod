package com.example.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.example.config.MongoConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Document(collection = "Subject")
public class Subject {
    private String name;
    
    
    private ArrayList<Lecturer> lecturerList;
    private ArrayList<String> timePrefered;
    private int expectedStudent;
    private Priority priority;
    private boolean isLabSubject;
    private ArrayList<ExceptionSet> setOn;
    
    @Id
    private String id;
    
	public Subject(){
		
	}

    public Subject(String name){
        this.name = name;
        this.lecturerList = new ArrayList<Lecturer>();
        this.id = this.generateID(null);
        this.priority = null;
        this.timePrefered = new ArrayList<String>();
        this.isLabSubject = false;
    }
    
    public Subject(String name, Priority priority, List<Lecturer> list, int num){
    	this.name = name;
        this.lecturerList = (ArrayList<Lecturer>) list;
        this.id = this.generateID(null);
        this.priority = priority;
        this.timePrefered = new ArrayList<String>();
        this.expectedStudent = num;
        this.isLabSubject = false;
    }
    
    
    public Subject(@JsonProperty("id") String id, @JsonProperty("name") String name,@JsonProperty("priority") Priority priority, @JsonProperty("lecturerList") List<Lecturer> list,@JsonProperty("expectedStudent") int num
    		,@JsonProperty("timePrefered") ArrayList<String> timePrefered
    		,@JsonProperty("isLabSubject") boolean isLabSubject){
    	this.name = name;
        this.lecturerList = (ArrayList<Lecturer>) list;
        this.id = this.generateID(id);
        this.priority = priority;
        this.timePrefered = timePrefered;
        this.expectedStudent = num;
        this.isLabSubject = isLabSubject;
        
    }
    
    
	public boolean isLabSubject() {
		return isLabSubject;
	}

	public void setLabSubject(boolean isLabSubject) {
		this.isLabSubject = isLabSubject;
	}

	public ArrayList<ExceptionSet> getSetOn() {
		return setOn;
	}

	public void setSetOn(ArrayList<ExceptionSet> setOn) {
		this.setOn = setOn;
	}

	public void setId(String id) {
		this.id = id;
	}


    public void setName(String name) {
		this.name = name;
	}

	public void setLecturerList(ArrayList<Lecturer> lecturerList) {
		this.lecturerList = lecturerList;
	}

	public void setTimePrefered(ArrayList<String> time){
		this.timePrefered = time;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public void setExpectedStudent(int number){
        this.expectedStudent = number;
    }

    public void addTimePrefer(DayName dayTime, Time time){
    	this.timePrefered.add(Integer.toString(dayTime.getValue()) + time.getStartTime() + time.getEndTime());
    }
    
  
    public ArrayList<String> getTimePrefered() {
		return this.timePrefered;
	}


    public Priority getPriority(){
        return this.priority;
    }

    public int getNumPriority(){

        return this.priority.getValue();
    }
    
    private String generateID(String id){
    	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    	if (id != null)
    		return id;
        String result = "";
        Random rand = new Random();
        for (int i = 0; i < 4; i++)
        	result += Character.toString(alphabet[rand.nextInt(25) + 0]);
        
        return result;
    }

    
    public void addLecturer(Lecturer l){
        this.lecturerList.add(l);
    }

    public void removeLecturer(String l){
        this.lecturerList.remove(l);
    }

    public String getName(){
        return this.name;
    }
    
    public int getExpectedStudent()
    {
    	return this.expectedStudent;
    }

    public String getId()
    {
    	return this.id;
    }
    
    public ArrayList<Lecturer> getLecturerList(){
    	return this.lecturerList;
    }
    
    public String toString(){
    	return this.getId() + " : " + this.getName() + " : " + this.getLecturerList() ;
    }
    
    
    


}
