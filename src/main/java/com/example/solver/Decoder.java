package com.example.solver;


import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.model.DayName;
import com.example.model.Room;
import com.example.model.Subject;
import com.example.model.TimeSlot;
import com.example.repository.RoomRepository;
import com.example.repository.ScheduleRepository;


public class Decoder{
	
	@Autowired
	private ScheduleRepository repository;
	@Autowired
	private RoomRepository roomRepository;
	
    private Map<Integer, String> termMap;
    
    public Decoder(Map<Integer, String> termMap) {
        this.termMap = termMap;   
    }

    public ArrayList<TimeSlot> decode(String str) {
        String[] array = str.split(" "); // placeholder for literals
        ArrayList<String> answerList = (ArrayList<String>) Arrays.asList(array).stream().map(item -> { return termMap.get(Integer.parseInt(item)); } ).collect(Collectors.toList());
        answerList = (ArrayList<String>) answerList.stream().filter(item -> item != null).collect(Collectors.toList());
        
        ArrayList<TimeSlot> slots = new ArrayList<TimeSlot>(); 
        ArrayList<String> datetimeAssignment =  (ArrayList<String>) answerList.stream().filter(item -> item.substring(0, 1).equals("0")).collect(Collectors.toList());
        ArrayList<String> roomAssignment = (ArrayList<String>) answerList.stream().filter(item -> item.substring(0, 1).equals("1")).collect(Collectors.toList());
        
        //Assign date time for subject first
        datetimeAssignment.forEach(item -> {
        	Subject subject = repository.findOne(item.substring(1,5));
        	TimeSlot slot = new TimeSlot();
        	slot.setSubject(subject);
        	slot.setStartTime(item.substring(6, 10));
        	slot.setDay(DayName.values()[Integer.parseInt(item.substring(5, 6))]);
        	slot.setEndTime(item.substring(10, item.length()));
        	slots.add(slot);
        	//Subject s = DataHandler.getSubjectByID(item.substring(1,5));
        	//t.addSubjectOn(s, DayName.values()[Integer.parseInt(item.substring(5, 6))], item.substring(6, 10), item.substring(10, item.length()));
        });
        
        //Assign room to that subject in slot
        roomAssignment.forEach(item -> {
        	TimeSlot subject = (TimeSlot) slots.stream().filter(element -> element.getId().equals(item.substring(1, 5)));
        	//Subject s = DataHandler.getSubjectByID(item.substring(1,5));
        	//Room r = DataHandler.getRoomByID( item.substring(5,item.length()) );
        	subject.setRoom( roomRepository.findOne(item.substring(5,item.length())));
        	//t.addRoomOnSubject(s, r);
        });
        return slots;
    }

}
