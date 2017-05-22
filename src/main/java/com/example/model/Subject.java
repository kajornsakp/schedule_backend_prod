package com.example.model;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "Subject")
public class Subject {
    private String name;
    
    @Transient
    private ArrayList<Lecturer> lecturerList;
    private ArrayList<String> timePrefered;
    private int expectedStudent;
    private Priority priority;
    
    private ExceptionSet setOn;
    
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
    }
    
    public Subject(String name, Priority priority, List<Lecturer> list, int num){
    	this.name = name;
        this.lecturerList = (ArrayList<Lecturer>) list;
        this.id = this.generateID(null);
        this.priority = priority;
        this.timePrefered = new ArrayList<String>();
        this.expectedStudent = num;
    }
    
    
    public Subject(@JsonProperty("id") String id, @JsonProperty("name") String name,@JsonProperty("priority") Priority priority, @JsonProperty("lecturerList") List<Lecturer> list,@JsonProperty("expectedStudent") int num
    		,@JsonProperty("dayPrefer") String day, @JsonProperty("startTime")String startTime, @JsonProperty("endTime") String endTime){
    	this.name = name;
        this.lecturerList = (ArrayList<Lecturer>) list;
        this.id = this.generateID(id);
        this.priority = priority;
        this.timePrefered = new ArrayList<String>();
        this.addTimePrefer(DayName.valueOf(day), new Time(startTime, endTime));
        this.expectedStudent = num;
    }
    
	public ExceptionSet getSetOn() {
		return setOn;
	}

	public void setSetOn(ExceptionSet setOn) {
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


    public int getPriority(){
    	return this.priority.getValue();
    }
    
    
    
    private String generateID(String id){
    	if (id != null)
    		return id;
        UUID tempID = UUID.randomUUID();
        String[] parts = tempID.toString().split("-");
        String result = "";
        int count = 0;
        while (count < 4){
            int a = (parts[1].charAt(count) + parts[2].charAt(count)) - 30;
            
            //result += (char) a;
            if (Character.isAlphabetic((char) a) || Character.isDefined((char) a)){
            	result += (char) a;
            	count++;
            }
        }
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
    	return this.getId() + " : " + this.getName() ;
    }
    
    
    


}
