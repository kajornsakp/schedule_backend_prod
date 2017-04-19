package com.example.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "Subject")
public class Subject {
    private String name;
    private ArrayList<String> lecturerList;
    private Map<DayName,ArrayList<Time>> timePrefered;
    private int expectedStudent;
    private int priority;
    private DayName subscribeDay;
    private Time subscribeTime;
    
    @Id
    private String id;
    
	public Subject(){
		
	}

    public Subject(String name){
        this.name = name;
        this.lecturerList = new ArrayList<String>();
        this.id = this.generateID();
        this.priority = 0;
        this.timePrefered = new HashMap<DayName,ArrayList<Time>>();
        
    }
    
    public Subject(@JsonProperty("name") String name,@JsonProperty("priority") int priority, @JsonProperty("lecturerList") List<String> list,@JsonProperty("expectedStudent") int num){
    	this.name = name;
        this.lecturerList = (ArrayList<String>) list;
        this.id = this.generateID();
        this.priority = priority;
        this.timePrefered = new HashMap<DayName,ArrayList<Time>>();
        this.expectedStudent = num;
    }
    
    

	public void setId(String id) {
		this.id = id;
	}

	
    public void setSubscribeDay(DayName subscribeDay) {
		this.subscribeDay = subscribeDay;
	}

	public void setSubscribeTime(Time subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

    public void setName(String name) {
		this.name = name;
	}

	public void setLecturerList(ArrayList<String> lecturerList) {
		this.lecturerList = lecturerList;
	}

	public void setTimePrefered(Map<DayName, ArrayList<Time>> timePrefered) {
		this.timePrefered = timePrefered;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setExpectedStudent(int number){
        this.expectedStudent = number;
    }

    public void setTimePrefer(DayName dayTime, Time time){
    	
    	if (this.timePrefered.get(dayTime) == null){
    		ArrayList<Time> newTime = new ArrayList<Time>();
    		newTime.add(time);
    		this.timePrefered.put(dayTime, newTime);
    	} else {
    		this.timePrefered.get(dayTime).add(time);
    	}
    }
    
    public Time getSubscribeTime() {
		return subscribeTime;
	}
    
    public Map<DayName, ArrayList<Time>> getTimePrefered() {
		return timePrefered;
	}


	public DayName getSubscribeDay() {
		return subscribeDay;
	}
    
    public int getPriority(){
    	return this.priority;
    }
    
    public void subscribedDayTime(DayName day, Time time){
    	this.subscribeDay = day;
    	this.subscribeTime = time;
    }
    
    public boolean hasSubscribed(){
    	if (this.subscribeDay == null)
    		return false;
    	return true;
    }
    
    private String generateID(){
        UUID tempID = UUID.randomUUID();
        String[] parts = tempID.toString().split("-");
        String result = "";
        for (int i = 0 ; i < 4 ; i++){
            int a = (parts[1].charAt(i) + parts[2].charAt(i)) - 30;
            result += (char) a;

        }
        return result;
    }

    
    public void addLecturer(String l){
        this.lecturerList.add(l);
    }

    public void removeLecturer(Lecturer l){
        this.lecturerList.remove(l);
    }
    
    
    public ArrayList<DayName> findDay(){
    	ArrayList<DayName> days = new ArrayList<DayName>();
    	for (Entry<DayName, ArrayList<Time>> entry : this.timePrefered.entrySet()){
    		if (entry.getValue() != null)
    			days.add(entry.getKey());
    	}
    	return days;
    }
    
    public Time findTime(){
    	
    	return this.timePrefered.get(this.findDay().get(0)).get(0);
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
    
    public ArrayList<String> getLecturerList(){
    	return this.lecturerList;
    }
    
    public String toString(){
    	return this.getId() + " : " + this.getName() ;
    }
    
    
    


}
