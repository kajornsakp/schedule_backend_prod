package com.example.model;



import java.util.ArrayList;
import java.util.UUID;

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
    	
    	UUID tempID = UUID.randomUUID();
        String[] parts = tempID.toString().split("-");
        String result = "";
        for (int i = 0 ; i < 4 ; i++){
            int a = (parts[1].charAt(i) + parts[2].charAt(i)) - 30;
            result += (char) a;

        }
        return result;
    }
    
    public Room(){
    	
    }

    public Room(@JsonProperty("roomName") String name, @JsonProperty("capacity") int size, @JsonProperty("id") String id){
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
