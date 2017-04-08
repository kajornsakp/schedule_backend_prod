package com.example.model;


import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Subject")
public class Subject {
    private String name;
    private ArrayList<Lecturer> lecturerList;
    private Map<DayName,ArrayList<Time>> timePrefered;
    private int expectedStudent;
    private int priority;

    @Id
    private String id;

    public Subject(String name){
        this.name = name;
        this.lecturerList = new ArrayList<Lecturer>();
        this.id = this.generateID();
    }
    
    public int getPriority(){
    	return this.priority;
    }
    
    public void setPriority(int p){
    	this.priority = p;
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

    public void setID(String number){
        this.id = number;
    }

    public void setExpectedStudent(int number){
        this.expectedStudent = number;
    }

    public void addLecturer(Lecturer l){
        this.lecturerList.add(l);
    }

    public void removeLecturer(Lecturer l){
        this.lecturerList.remove(l);
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
    
    
    public ArrayList<DayName> getDay(){
    	ArrayList<DayName> days = new ArrayList<DayName>();
    	for (Entry<DayName, ArrayList<Time>> entry : this.timePrefered.entrySet()){
    		if (entry.getValue() != null)
    			days.add(entry.getKey());
    	}
    	return days;
    }
    
    public String[] getTime(){
    	return (String[]) this.timePrefered.get(this.getDay().get(0)).toArray();
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
    
    public String toString(){
    	return this.getId() + " : " + this.getName() ;
    }


}
