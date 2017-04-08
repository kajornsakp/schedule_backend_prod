package com.example.model;



import java.util.ArrayList;
import java.util.UUID;



public class Room {
    private String roomName;
    private int capacity;

//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
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

   

}
