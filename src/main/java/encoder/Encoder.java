package encoder;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import model.Day;
import model.DayName;
import model.Room;
import model.Slot;
import model.Subject;

public class Encoder {
	ArrayList<Room> rooms;
	ArrayList<Slot> slots;
	Map<DayName, String> enumDayMap;
	    
	public Encoder()
	{
		rooms = new ArrayList<>();
		slots = new ArrayList<>();
		enumDayMap = new EnumMap<DayName, String>(DayName.class);
		enumDayMap.put(DayName.MONDAY, "1");
		enumDayMap.put(DayName.TUESDAY, "2");
		enumDayMap.put(DayName.WEDNESDAY, "3");
		enumDayMap.put(DayName.THURSDAY, "4");
		enumDayMap.put(DayName.FRIDAY, "5");
	}
	public void setRooms(ArrayList<Room> r)
	{
		this.rooms = r;
	}
	public void setSlots(ArrayList<Slot> s)
	{
		this.slots = s;
	}
	public String encode()
	{
		
		String courseTerm;
		String resourceTerm;
		String innerterm;
		String term="";
		term+="*(";
		ArrayList<Subject> subjects = Subject.findAll();
		ArrayList<Day> days = Day.findAll();
		ArrayList<Slot> slots = Slots.findAll();
		ArrayList<Room> rooms = Rooms.findAll();
		for(int i=0;i<subjects.size();i++)
		{
			courseTerm = encodeCourse(subjects,days,slots,subjects.get(i));
			resourceTerm = encodeResource(rooms,days,slots,subjects.get(i).getStudentCount());
			innerterm = "*("+courseTerm+" "+resourceTerm+")";
			term+=innerterm+" ";
		}
		term+=")";
		
		String output = "";
		// TODO: call SAT solver
		// output = SATsolver.solve
		
		return output;
	}
	public String encodeCourse(ArrayList<Subject> allSubjs,ArrayList<Day> allDays,ArrayList<Slot> allSlots,Subject subj)
	{
		// pick only courses that are not that subject
		// format 0ssssDtttt
		allSubjs.remove(subj);
		
		String result="+("+subj.getId()+" ";
		result += "+(";
		for(int i = 0;i< allSubjs.size();i++)
		{
			for(int j=0;j<allDays.size();j++){
				for(int k=0;k<allSlots.size();k++){
					String time=allSlots.get(k).getStartTime();
					// remove all nonnumeric character
					time = time.replaceAll("[^\\d.]", "");
					String day = enumDayMap.get(allDays.get(j).getDay());
					// format 0ssssDtttt
					result += "0"+allSubjs.get(i).getId()+day+time+" ";
				}
			}
		}
		result+= "))";
		return result;
		
	}
	public String encodeResource(ArrayList<Room> rooms,ArrayList<Day> days,ArrayList<Slot> slots,int studentCount)
	{
		// format for room = 1rrrrDtttt
		// format for fit = 2rrrrnnnn ; n= number of students
		// TODO: add weight 
		// TODO: get subject ID format
		// TODO: get room ID format
		String result="+(";
		for(int i=0;i<rooms.size();i++)
		{
			for(int j=0;j<days.size();j++)
			{
				for(int k=0;k<slots.size();k++){
					String time=slots.get(k).getStartTime();
					// remove all nonnumeric character
					time = time.replaceAll("[^\\d.]", "");
					String day = enumDayMap.get(days.get(j).getDay());
					// format 0ssssDtttt 
					// format 2rrrrnnnn
					String fit = "2"+ rooms.get(i).getID + studentCount;
					
					result+="*("+"1"+rooms.get(i).getID()+day+time+" "+fit+")";
				}
			}
		}
		result+=")";
		return result;
	}
}
