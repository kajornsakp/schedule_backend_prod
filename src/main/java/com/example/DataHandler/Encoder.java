package com.example.DataHandler;


//SAT4J Dependencies
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.maxsat.reader.WDimacsReader;
import org.sat4j.pb.IPBSolver;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.*;


//JAVA standard Libs
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;


//Custom import
import com.example.model.Room;
import com.example.model.Slot;
import com.example.model.Subject;
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

    public Encoder() {
        initDayMapper();
        termMap = new HashMap<>();
        reverseTermMap = new HashMap<>();
        rooms = (ArrayList<Room>) DataHandler.getAllRooms();
        subjects = (ArrayList<Subject>) DataHandler.getAllSubjects();
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

    public String encode() {
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
            possibleCourseCNF += subjects.get(i).getPriority() + " ";
            for (int j = 0; j < subjects.get(i).getTimePrefered().size(); j++) {
                int maxFitWeight = findMaxFitWeight(subjects.get(i), rooms);
                if (maxFitWeight != -1) // if there are some rooms that fit amount of student
                    resourceCNF += maxFitWeight + " ";
                else // no room fits subject // weight = 100 , not assign to any room
                    resourceCNF+= 100 + " ";       
                dateTime = subjects.get(i).getTimePrefered().get(j);
                possibleCourseCNF += encodePossibleCourse(subjects.get(i), dateTime.substring(0, 1), new Slot(dateTime.substring(1, 6), dateTime.substring(6, 11))) + " ";
                for (int l = 0; l < rooms.size(); l++) {
                    // encode course and exceptionset
                    if(rooms.get(l).getCapacity() >= subjects.get(i).getExpectedStudent()) {
                        courseCNF += encodeCourse(subjects, rooms.get(l), dateTime.substring(0, 1), new Slot(dateTime.substring(1, 6), dateTime.substring(6, 11)), subjects.get(i));
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

        ////////////////////////  SAT4J  /////////////////////////////////////

        IPBSolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        WeightedMaxSatDecorator decorator = new WeightedMaxSatDecorator(solver);
        WDimacsReader reader = new WDimacsReader(decorator);

        String ans = "";
        // CNF filename is given on the command line
        try {
            IProblem problem = reader.parseInstance("cnfInput.txt");
            
            if (problem.isSatisfiable()) {
                ans = reader.decode(problem.findModel());
                //Ploy's note: just ignore '-null' or 'null' (bug)
                //& current solution : satisfy max clauses then max literals
        
            } else {
                System.out.println("Unsatisfiable !");
                System.out.println(reader.decode(problem.findModel()));
                reader = new WDimacsReader(decorator);
                problem = reader.parseInstance("cnfInput.txt");
                System.out.println(problem.isSatisfiable() + ":satisfiable");
                System.out.println(reader.decode(problem.findModel()));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (ParseFormatException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }

        try {
            PrintWriter out = new PrintWriter("cnfOutput.txt");
            out.print(ans);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return ans;

    }

    private int findMaxFitWeight(Subject subject, ArrayList<Room> rooms) {
        int difference = 0;
        boolean isAssigned = false;
        for (int i = 0; i < rooms.size(); i++) {
            // check whether room fits
            if (rooms.get(i).getCapacity() >= subject.getExpectedStudent()) {
                difference = rooms.get(i).getCapacity() - subject.getExpectedStudent();
                isAssigned = true;
            }
        }
        if (isAssigned)
            return 100 - difference;
        // if no rooms fits amount of student
        return -1;

    }

    private String encodeCourse(ArrayList<Subject> subjects, Room room, String day, Slot slot, Subject subj) {
        ArrayList<Subject> otherSubjectList = (ArrayList<Subject>) subjects.clone();
        otherSubjectList.remove(subj);
        // clauseArr
        String courseAndResourceConstraint = "";
        String exceptionSetConstraint = "";
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
            // '\n' = 'and'
            // exception set
            exceptionSetConstraint += MAX_WEIGHT + " " + "-" + termMap.get(subjectFormat) + " -" + termMap.get(otherSubjFormat) + " 0\n";

            clauseCount += 2;
        }
        resultConstraint = courseAndResourceConstraint + exceptionSetConstraint;

        return resultConstraint;
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
