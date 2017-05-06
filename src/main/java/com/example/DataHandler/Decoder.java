package com.example.DataHandler;


import java.util.*;

import com.example.model.Day;
import com.example.model.Subject;


public class Decoder{
    private Map<Integer, String> termMap;
    private ArrayList<String> assignedSubject;
    private ArrayList<String> assignedRoom;
    public Decoder(Map<Integer, String> termMap) {
        this.termMap = termMap;
        assignedSubject =  new ArrayList<>();
        assignedRoom = new ArrayList<>();
    }

    public void decode(String str) {
        System.out.println();
        String literal;
        String[] array = str.split(" "); // placeholder for literals
        ArrayList<String> answerList = new ArrayList(Arrays.asList(array));
        
        ArrayList<Day> subjectList = new ArrayList<Day>();
        
        //System.out.println("last"+array[array.length-1]);
        for (int i = 0; i < array.length - 1; i++) {
            if (Integer.parseInt(array[i]) > 0) {
                literal = termMap.get(Integer.parseInt(array[i]));
                //String subjectID = literal.substring(1, 5);
                //Subject s = DataHandler.getSubjectByID(subjectID);
                      
                System.out.println("test : " + literal);
                if(literal.substring(0,1).equals("0")) // begin with '0' means datetime assignment, begin with '1' means room assignment
                {
                	
                }
            } else {
                literal = "-" + termMap.get(Math.abs(Integer.parseInt(array[i])));
            }

//            System.out.println(literal);

        }
        System.out.println();
    }

    public void addAssignedSubject(String term)
    {

    }


}
