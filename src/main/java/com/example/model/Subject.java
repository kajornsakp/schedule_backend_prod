package com.example.model;


import java.util.ArrayList;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "Subject")
public class Subject {
    private String name;
    private ArrayList<Lecturer> lecturerList;
    private String dayTimePrefer;
    private int expectedStudent;

    @Id
    private String id;

    public Subject(String name){
        this.name = name;
        this.lecturerList = new ArrayList<Lecturer>();
        this.id = this.generateID();
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

    public void setDayTimePrefer(String dayTime){
        this.dayTimePrefer = dayTime;
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


}
