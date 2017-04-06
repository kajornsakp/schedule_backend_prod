package com.example.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;




@Document(collection = "Room")
public class Room {
    private String roomName;
    private ArrayList<Day> days;
    private int capacity;


    private String id;
    
    private String generateID()
    {
    	UUID tempID = UUID.randomUUID();
        String[] parts = tempID.toString().split("-");
        String result = "";
        for (int i = 0 ; i < 4 ; i++){
            int a = (parts[1].charAt(i) + parts[2].charAt(i)) - 30;
            result += (char) a;

        }
        return result;
    }

    public Room(String name, int size){
        this.roomName = name;
        this.capacity = size;
        this.id = generateID();
        days = new ArrayList<Day>();
        days.add(new Day(DayName.MONDAY));
        days.add(new Day(DayName.TUESDAY));
        days.add(new Day(DayName.WEDNESDAY));
        days.add(new Day(DayName.THURSDAY));
        days.add(new Day(DayName.FRIDAY));
    }

    public boolean addSlot(DayName day, Slot s){
        return this.getDay(day).reserveSlot(s);
    }

    public String getRoomName(){
        return this.roomName;
    }
    
    public String getId()
    {
    	return this.id;
    }

    public Day getDay(DayName day){
        for (Day d : days){
            if (d.getDay().equals(day))
                return d;
        }
        throw new IllegalArgumentException("no such day");
    }

    public String toString(){
        String result = this.roomName + "\n===========";
        for (Day d : days){
            result += d.toString();
        }
        return result;
    }

    public ArrayList<Slot> getAvailableSlot(ArrayList<Slot> defaultSlot ,DayName d){
        ArrayList<Slot> clear = new ArrayList<Slot>();
        ArrayList<Slot> used = this.getDay(d).getSlotUsed();
        for (Slot s : defaultSlot) {
            if (!used.contains(s))
                clear.add(s);
        }
        return clear;
    }


}
