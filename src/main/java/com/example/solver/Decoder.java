package com.example.solver;


import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;


import com.example.config.MongoConfig;
import com.example.model.DayName;
import com.example.model.Room;
import com.example.model.Subject;
import com.example.model.TimeSlot;


public class Decoder{
	

    private Map<Integer, String> termMap;
    
    public Decoder(Map<Integer, String> termMap) {
        this.termMap = termMap;   
    }

    public ArrayList<TimeSlot> decode(String str) {
        System.out.println(str);
        str = str.substring(0, str.length()-2);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MongoConfig.class);
    	MongoOperations mongoOperations = (MongoOperations) applicationContext.getBean("mongoTemplate");
        String[] array = str.split(" "); // placeholder for literals

        
        ArrayList<String> answerList = (ArrayList<String>) Arrays.asList(array)
        		.stream()
        		.map(item -> { 
        			if(Integer.parseInt(item) > 0){
        	// the statement is true
        			return termMap.get(Integer.parseInt(item));
        			}else{ return "";} }).collect(Collectors.toList());
        
        answerList = (ArrayList<String>) answerList.stream().filter(item -> (item != null && item!="" )).collect(Collectors.toList());

        ArrayList<TimeSlot> slots = new ArrayList<TimeSlot>(); 
        ArrayList<String> datetimeAssignment =  (ArrayList<String>) answerList.stream().filter(item -> item.substring(0, 1).equals("0")).collect(Collectors.toList());
        ArrayList<String> roomAssignment = (ArrayList<String>) answerList.stream().filter(item -> item.substring(0, 1).equals("1")).collect(Collectors.toList());

        //Assign date time for subject first
        datetimeAssignment.forEach(item -> {
        	Subject subject = mongoOperations.findById((item.substring(1,5)),Subject.class);
        	TimeSlot slot = new TimeSlot();
        	slot.setSubject(subject);
        	slot.setStartTime(item.substring(6, 10));
        	slot.setDay(DayName.values()[Integer.parseInt(item.substring(5, 6)) - 1]);
        	slot.setEndTime(item.substring(10, item.length()));
        	slots.add(slot);
        	//Subject s = DataHandler.getSubjectByID(item.substring(1,5));
        	//t.addSubjectOn(s, DayName.values()[Integer.parseInt(item.substring(5, 6))], item.substring(6, 10), item.substring(10, item.length()));
        });
        
        
        //Assign room to that subject in slot
        
        roomAssignment.forEach(item -> {
        	//System.out.println("check item : " + item);
        	TimeSlot subject = ((List<TimeSlot>) slots.stream().filter(element -> element.getSubject().getId().equals(item.substring(1, 5))).collect(Collectors.toList())).get(0);
        	//Subject s = DataHandler.getSubjectByID(item.substring(1,5));
        	
        	//Room r = DataHandler.getRoomByID( item.substring(5,item.length()) );
        	//System.out.println("check room decoder id : " + (item.substring(5,item.length())));
        	subject.setRoom(mongoOperations.findById((item.substring(5,item.length())), Room.class));
        	//t.addRoomOnSubject(s, r);
        });

//        List<Subject> subjects = mongoOperations.findAll(Subject.class);
//        for (int i = 0 ; i <  subjects.size(); i++){
//            boolean contain = false;
//            for (int j = 0 ; j < slots.size(); j++){
//                if (slots.get(j).getSubject().getId().equals(subjects.get(i).getId()))
//                    contain = true;
//
//            }
//            if (contain)
//                slots.add(new TimeSlot(subjects.get(i)));
//        }

        return slots;
    }

}
