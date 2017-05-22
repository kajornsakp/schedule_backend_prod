package com.example.model;



import java.util.Random;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "Room")
public class Room {
    private String roomName;
    private int capacity;

//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
    private String id;
    
    private String generateID(String id)
    {
    	if (id != null)
    		return id;
    	
    	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    	if (id != null)
    		return id;
        String result = "";
        Random rand = new Random();
        for (int i = 0; i < 4; i++)
        	result += Character.toString(alphabet[rand.nextInt(25) + 0]);
        
        return result;
    }
    
    public Room(){
    	
    }

    public Room(@JsonProperty("roomName") String name, @JsonProperty("capacity") int size){
        this.roomName = name;
        this.capacity = size;
        this.id = generateID(id);
       
    }

    public String getRoomName(){
        return this.roomName;
    }
    
    public int getCapacity(){
    	return this.capacity;
    }
    
    public String getId()
    {
    	return this.id;
    }
    
    @Override
    public String toString(){
    	return this.roomName;
    }

   

}
