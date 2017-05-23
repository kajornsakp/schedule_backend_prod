package com.example.solver;


//SAT4J Dependencies

import org.springframework.beans.factory.annotation.Autowired;

//JAVA standard Libs
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Custom import
import com.example.model.Room;
import com.example.model.Slot;
import com.example.model.Subject;
import com.example.repository.RoomRepository;
import com.example.repository.ScheduleRepository;
import com.example.controller.ExternalDataHandler;
import com.example.model.DayName;

/**
 * Created by Jrphapa on 4/5/2017 AD.
 */

public class Encoder {

    ArrayList<Room> rooms;
   
    ArrayList<Subject> subjects;
    Map<DayName, String> enumDayMap;
    Map<String, Integer> termMap;
    Map<Integer, String> reverseTermMap;
    
    private int varCount; // max var
    private int clauseCount; // no. of clauses
    private final String MAX_WEIGHT = "100000000000000000";
    private final String WEIGHT = "1";

    public Encoder(List<Room> room, List<Subject> subject) {
    	System.out.println("check room : " + room);
        initDayMapper();
        termMap = new HashMap<>();
        reverseTermMap = new HashMap<>();
        rooms = (ArrayList<Room>) room;
        subjects = (ArrayList<Subject>) subject;
        varCount = 0;
        clauseCount = 0;
    }

    private void initDayMapper() {
        enumDayMap = new EnumMap<DayName, String>(DayName.class);
        enumDayMap.put(DayName.MONDAY, "1");
        enumDayMap.put(DayName.TUESDAY, "2");
        enumDayMap.put(DayName.WEDNESDAY, "3");
        enumDayMap.put(DayName.THURSDAY, "4");
        enumDayMap.put(DayName.FRIDAY, "5");
    }

    public void encode() {
    	long startTime = System.nanoTime();
        //reset counter
        varCount = 0;
        clauseCount = 0;
        // clause for providing course and resource constraint along with exception set
        String courseCNF = "";
        // clause for providing resource constraint
        String resourceCNF = "";
        // clause for giving possible course arrangement in date, time
        String possibleCourseCNF = "";
        // term is the input for SAT solver
        String finalTerm = "p wcnf";
        String dateTime;
        
        // loop generate all clauses for input for SAT solver
        for (int i = 0; i < subjects.size(); i++) {

            possibleCourseCNF += subjects.get(i).getNumPriority() + " ";
            for (int j = 0; j < subjects.get(i).getTimePrefered().size(); j++) {
                int maxFitWeight = findMaxFitWeight(subjects.get(i), rooms);
                if (maxFitWeight != -1) // if there are some rooms that fit amount of student
                    resourceCNF += maxFitWeight + " ";
                else // no room fits subject // weight = 100 , not assign to any room
                    resourceCNF+= 100 + " ";       
                dateTime = subjects.get(i).getTimePrefered().get(j);
                System.out.println("check datetime : " + dateTime);
                
                possibleCourseCNF += encodePossibleCourse(subjects.get(i), dateTime.substring(0, 1), new Slot(dateTime.substring(1, 3) + ":" + dateTime.substring(3, 5), dateTime.substring(5, 7) + ":" +dateTime.substring(7,dateTime.length()))) + " ";
                for (int l = 0; l < rooms.size(); l++) {
                    // encode course and exceptionset
                    if(rooms.get(l).getCapacity() >= subjects.get(i).getExpectedStudent()) {
                        courseCNF += encodeCourse(subjects, rooms.get(l), dateTime.substring(0, 1),  new Slot(dateTime.substring(1, 3) + ":" + dateTime.substring(3, 5), dateTime.substring(5, 7) + ":" +dateTime.substring(7,dateTime.length())), subjects.get(i));
                        resourceCNF += encodeResource(subjects.get(i), rooms.get(l)) + " ";
                    }
                }
                // '0\n' is 'and'
                resourceCNF += "0\n";
                clauseCount++;
            }
            // '0\n' is 'and'
            possibleCourseCNF += "0\n";
            clauseCount++;
        }

        finalTerm += " " + varCount + " " + clauseCount + " " + MAX_WEIGHT + "\n";
        finalTerm += courseCNF + resourceCNF + possibleCourseCNF;

        // save cnf to file
        try {
            PrintWriter out = new PrintWriter("cnfInput.txt");
            out.print(finalTerm);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        long endTime = System.nanoTime();
        System.out.println("encoder takes time : " + (endTime - startTime) / 1000000);


    }

    private int findMaxFitWeight(Subject subject, ArrayList<Room> rooms) {
        //int difference = 0;
        boolean isAssigned = false;
        int percentage;
        int maxPercentage = 0;
        for (int i = 0; i < rooms.size(); i++) {
            // check whether room fits
            if (rooms.get(i).getCapacity() >= subject.getExpectedStudent()) {
//            	percentage is a population density in room , more density means room fit better
            	percentage = (subject.getExpectedStudent()/rooms.get(i).getCapacity())*100;
            	if(percentage > maxPercentage)
            		maxPercentage = percentage;
                //difference = rooms.get(i).getCapacity() - subject.getExpectedStudent();
                isAssigned = true;
            }
        }
        if (isAssigned)
            //return 100 - difference;
        	return maxPercentage;
        // if no rooms fits amount of student
        return -1;

    }

    private String encodeCourse(ArrayList<Subject> subjects, Room room, String day, Slot slot, Subject subj) {
        ArrayList<Subject> otherSubjectList = (ArrayList<Subject>) subjects.clone();
        otherSubjectList.remove(subj);
        // clauseArr
        String courseAndResourceConstraint = "";
        String exceptionSetConstraint = "";
        String sameLecturerConstraint = "";
        Boolean hasLecturerConflict = Boolean.FALSE;
        String resultConstraint;
        // format 0ssssDtttt for subject
        // format 1ssssrrrr for subject in room
        String subjectFormat = "0" + subj.getId() + day + slot.getStartTime().replaceAll("[^\\d.]", "")+ slot.getEndTime().replaceAll("[^\\d.]", "");
        String roomFormat = "1" + subj.getId() + room.getId();
        // term mapper maps term with integer (positive)
        // sat4j requires integer as an input for term
        if (!termMap.containsKey(subjectFormat)) {
            varCount++;
            termMap.put(subjectFormat, varCount);
            reverseTermMap.put(varCount, subjectFormat);
        }
        if (!termMap.containsKey(roomFormat)) {
            varCount++;
            termMap.put(roomFormat, varCount);
            reverseTermMap.put(varCount, roomFormat);
        }
        // loop generate clauses
        for (int j = 0; j < otherSubjectList.size(); j++)  // loop to create all subject' (other subject) 'then' term
        {
            // format 0ssssDtttt for subject
            String otherSubjFormat = "0" + otherSubjectList.get(j).getId() + day + slot.getStartTime().replaceAll("[^\\d.]", "")+ slot.getEndTime().replaceAll("[^\\d.]", "");
            if (!termMap.containsKey(otherSubjFormat)) {
                varCount++;
                termMap.put(otherSubjFormat, varCount);
                reverseTermMap.put(varCount, otherSubjFormat);
            }
            // format 1ssssrrrr for subject in room
            // roomFormat2 for room that contain other subjects
            String roomFormat2 = "1" + otherSubjectList.get(j).getId() + room.getId();
            if (!termMap.containsKey(roomFormat2)) {
                varCount++;
                termMap.put(roomFormat2, varCount);
                reverseTermMap.put(varCount, roomFormat2);
            }
            // couse and resource , 'or' together
            courseAndResourceConstraint += MAX_WEIGHT + " " + "-" + termMap.get(subjectFormat) + " -" + termMap.get(roomFormat) + " -" + termMap.get(otherSubjFormat) + " -" + termMap.get(roomFormat2) + " 0";
            courseAndResourceConstraint += "\n";
            clauseCount++;
            // '\n' = 'and'
            // exception set
            if (subj.getSetOn() != null)
            {
            	if (subj.getSetOn().contains(otherSubjectList.get(j).getSetOn()))
            	{
            		exceptionSetConstraint += MAX_WEIGHT + " " + "-" + termMap.get(subjectFormat) + " -" + termMap.get(otherSubjFormat) + " 0\n";
            		clauseCount++;
            		
            	}
            		
            }
            
            //clauseCount += 2;
        }
        sameLecturerConstraint = encodeSameLecturerCourse(subj,subjectFormat,day,slot);
        resultConstraint = courseAndResourceConstraint + exceptionSetConstraint;
        if(sameLecturerConstraint!="")
        	resultConstraint += sameLecturerConstraint;
       

        return resultConstraint;
    }
    // encode other subjects that lecturer has into constriant
    // lecturer can not teach more than 1 subject at the same time
    private String encodeSameLecturerCourse(Subject subject,String subjectFormat,String day,Slot slot)
    {
    	String result = "";
    	ArrayList<String> ids = ExternalDataHandler.getOtherByID(subject);
    	// the lecturer does not teach any other subject
    	if(ids.isEmpty())
    		return "";
    	// loop through all other subjects that has taught by the same lecturer
    	for(int i=0;i<ids.size();i++)
    	{
    		 String otherSubjFormat = "0" + ids.get(i) + day + slot.getStartTime().replaceAll("[^\\d.]", "")+ slot.getEndTime().replaceAll("[^\\d.]", "");
             if (!termMap.containsKey(otherSubjFormat)) {
                 varCount++;
                 termMap.put(otherSubjFormat, varCount);
                 reverseTermMap.put(varCount, otherSubjFormat);
             }
    		result+= MAX_WEIGHT + " " + "-" + termMap.get(subjectFormat) + " -" + termMap.get(otherSubjFormat) + " 0\n";
    		clauseCount++;
    	}
    	
    	return result;
    }

    private String encodeResource(Subject subj, Room room) {
        String result = "";
        String temp;
        // format for heldResource = 2ssssrrrr

        temp = "1" + subj.getId() + room.getId();

        if (!termMap.containsKey(temp)) {
            varCount++;
            termMap.put(temp, varCount);
            reverseTermMap.put(varCount, temp);
        }
        result+=termMap.get(temp);
        return result;
    }

    private String encodePossibleCourse(Subject subj, String day, Slot slot) {
        String result;
        result = "0" + subj.getId() + day + slot.getStartTime().replaceAll("[^\\d.]", "") + slot.getEndTime().replaceAll("[^\\d.]", "");
        if (!termMap.containsKey(result)) {
            varCount++;
            termMap.put(result, varCount);
            reverseTermMap.put(varCount, result);
        }
        return "" + termMap.get(result);
    }

    public Map<String, Integer> getTermMap() {
        return termMap;
    }

    public Map<Integer, String> getReverseTermMap() {
        return reverseTermMap;
    }


}
