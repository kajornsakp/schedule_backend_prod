package com.example.DataHandler;


import java.util.*;
import java.util.stream.Collectors;

import com.example.JacksonModel.TimetableWrapper;
import com.example.model.DayName;
import com.example.model.Room;
import com.example.model.Subject;


public class Decoder{
    private Map<Integer, String> termMap;
    
    public Decoder(Map<Integer, String> termMap) {
        this.termMap = termMap;   
    }

    public TimetableWrapper decode(String str) {
        String[] array = str.split(" "); // placeholder for literals
        ArrayList<String> answerList = (ArrayList<String>) Arrays.asList(array).stream().map(item -> { return termMap.get(Integer.parseInt(item)); } ).collect(Collectors.toList());
        answerList = (ArrayList<String>) answerList.stream().filter(item -> item != null).collect(Collectors.toList());
        
        TimetableWrapper t = new TimetableWrapper();
        ArrayList<String> datetimeAssignment =  (ArrayList<String>) answerList.stream().filter(item -> item.substring(0, 1).equals("0")).collect(Collectors.toList());
        ArrayList<String> roomAssignment = (ArrayList<String>) answerList.stream().filter(item -> item.substring(0, 1).equals("1")).collect(Collectors.toList());
        
        //Assign date time for subject first
        datetimeAssignment.forEach(item -> {
        	Subject s = DataHandler.getSubjectByID(item.substring(1,5));
        	t.addSubjectOn(s, DayName.values()[Integer.parseInt(item.substring(5, 6))], item.substring(6, 10), item.substring(10, item.length()));
        });
        
        //Assign room to that subject in slot
        roomAssignment.forEach(item -> {
        	Subject s = DataHandler.getSubjectByID(item.substring(1,5));
        	Room r = DataHandler.getRoomByID( item.substring(5,item.length()) );
        	t.addRoomOnSubject(s, r);
        });
        return t;
    }

}
