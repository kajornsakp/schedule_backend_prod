package com.example.model;

/**
 * Created by ShubU on 3/9/2017.
 */
public class Slot {
    private String startTime;
    private String endTime;
    private Subject course;
    private String roomName;
    //input format 18:00
    public Slot(String start, String end, Subject course, String name){

        //manipulate input (1 digit number to 2)
        String[] parts = start.split(":");
        String[] parts2 = end.split(":");
        for (String s : parts)
            if (s.length() == 1)
                s = "0" + s;
        for (String s : parts2)
            if (s.length() == 1)
                s = "0" + s;

        this.course = course;
        this.roomName = name;
        this.startTime = parts[0] + ":" + parts[1];
        this.endTime = parts2[0] + ":" + parts2[1];

    }

    public void setCourse(Subject course){
        this.course = course;
    }
    
    public void setRoomName(String name){
    	this.roomName = name;
    }

    //input in minute
    public Slot(String start, int period){

        String[] parts = start.split(":");
        int sHr = Integer.parseInt(parts[0]);
        int sMin = Integer.parseInt(parts[1]);
        sHr += (period / 60);
        sMin += (period % 60);
        if (sMin >= 60){
            sHr += 1;
            sMin -= (sMin - 60);
        }
        this.startTime = start;
        this.endTime = Integer.toString(sHr) + ":" + Integer.toString(sMin);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    
    public Subject getCourse(){
    	return this.course;
    }
    
    public String getRoomName(){
    	return this.roomName;
    }

    @Override
    public boolean equals(Object obj) {
        Slot cmp = (Slot)obj;
        if (this.getStartTime() == cmp.getStartTime() && this.getEndTime() == cmp.getEndTime() && this.getRoomName() == cmp.getRoomName())
            return true;
        return false;

    }

    @Override
    public String toString(){
        if (this.course != null)
            return this.course.getName() + " at room : " + this.getRoomName() + " => " + this.startTime + " - " + this.endTime;
        else
            return "unknown course at room : " + this.getRoomName() + " => " + this.startTime + " - " + this.endTime;



    }

}
