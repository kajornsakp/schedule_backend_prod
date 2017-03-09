package model;

import java.util.ArrayList;

public class Subject {
    private String name;
    private ArrayList<Lecturer> lecturerList;
    private String dayTimePrefer;

    public Subject(String name){
        this.name = name;
        this.lecturerList = new ArrayList<Lecturer>();
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




}
