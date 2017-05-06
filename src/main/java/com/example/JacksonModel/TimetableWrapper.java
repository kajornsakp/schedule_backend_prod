package com.example.JacksonModel;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;

import com.example.model.Day;
import com.example.model.DayName;
import com.example.model.Room;
import com.example.model.Slot;
import com.example.model.Subject;
import com.example.model.Time;

public class TimetableWrapper {
	private ArrayList<Day> dayList;
	public TimetableWrapper(){
		dayList = new ArrayList<Day>();
		dayList.add(new Day(DayName.MONDAY));
		dayList.add(new Day(DayName.TUESDAY));
		dayList.add(new Day(DayName.WEDNESDAY));
		dayList.add(new Day(DayName.THURSDAY));
		dayList.add(new Day(DayName.FRIDAY));
	}
	
	public Day getDay(DayName name){
		for (int i = 0 ; i < dayList.size(); i++)
			if (dayList.get(i).getDay().equals(name))
				return dayList.get(i);
		throw new IllegalArgumentException("no day founded");
	}
	
	public String getEndTime(Subject s, String startTime){
		ArrayList<String> preferred = s.getTimePrefered();
		for (int i = 0 ; i < preferred.size(); i++){
			String item = preferred.get(i);
			if (item.substring(1, 6).equals(startTime))
				return item.substring(6,item.length());
		}
		throw new IllegalArgumentException("perfered time does not match with start time");
	}
	
	public void addSubjectOn(Subject s, DayName name, String time){
		String startTime = time.substring(0,2) + ":" + time.substring(2,time.length());
		String endTime = this.getEndTime(s, startTime);
		
		Day day = this.getDay(name);
		Slot slot = new Slot(startTime, endTime);
		slot.setCourse(s);
		day.reserveSlot(slot);
	}
	
	public void addRoomOnSubject(Subject s, Room room){
		System.out.println("subject : " + s.getName());
		for (int j = 0; j < dayList.size();j++){
			ArrayList<Slot> slots = dayList.get(j).getSlotUsed();
			for (int i = 0 ; i < slots.size(); i++){
				System.out.println("Course in slot " + slots.get(i).getCourse().getName());
				if (slots.get(i).getCourse().getName().equals(s.getName())){
					System.out.println("get in!!!");
					slots.get(i).setRoom(room);
				}
			}	
		}
	}

	public ArrayList<Day> getDayList() {
		return dayList;
	}

	public void setDayList(ArrayList<Day> dayList) {
		this.dayList = dayList;
	}
	
	
	
}
