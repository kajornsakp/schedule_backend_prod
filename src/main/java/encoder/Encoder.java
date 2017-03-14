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
import Controller.DataManager;

public class Encoder {
	ArrayList<Room> rooms;
	ArrayList<Slot> slots;
	ArrayList<Subject> subjects;
	ArrayList<Day> days;
	Map<DayName, String> enumDayMap;
	DataManager dataManager;
	public Encoder()
	{
		initDayMapper();
		rooms = dataManager.getRoomList();
		slots = dataManager.getSlotList();
		subjects = dataManager.getSubjectList();
		days = dataManager.getDayList();
	}
	
	private void initDayMapper()
	{
		enumDayMap = new EnumMap<DayName, String>(DayName.class);
		int value = 1;
		// enumDayMap.put(DayName.MONDAY, "1");
		for (DayName d : DayName.values()) {
		     enumDayMap.put(d, Integer.toString(value));
		     value++;
		 }
	}
	
	public String encode()
	{
		
		String courseTerm;
		String resourceTerm;
		String innerterm;
		String term = "";
		
		// term is the input for SAT solver
		term+="*(";
		
		for(int i=0;i<subjects.size();i++)
		{
			courseTerm = encodeCourse(subjects,days,slots,subjects.get(i));
			resourceTerm = encodeResource(rooms,days,slots,subjects.get(i).getExpectedStudent());
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
					String fit = "2"+ rooms.get(i).getId() + studentCount;
					
					result+="*("+"1"+rooms.get(i).getId()+day+time+" "+fit+")";
				}
			}
		}
		result+=")";
		return result;
	}
}
