package com.example.JacksonModel;


import java.util.ArrayList;

import com.example.model.Day;
import com.example.model.DayName;
import com.example.model.Room;
import com.example.model.Slot;
import com.example.model.Subject;

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
	
	
	public void addSubjectOn(Subject s, DayName name, String sTime, String eTime){
		String startTime = sTime.substring(0,2) + ":" + sTime.substring(2,sTime.length());
		String endTime = sTime.substring(0,2) + ":" + sTime.substring(2,sTime.length());
		
		Day day = this.getDay(name);
		Slot slot = new Slot(startTime, endTime);
		slot.setCourse(s);
		day.reserveSlot(slot);
	}
	
	public void addRoomOnSubject(Subject s, Room room){
		for (int j = 0; j < dayList.size();j++){
			ArrayList<Slot> slots = dayList.get(j).getSlotUsed();
			for (int i = 0 ; i < slots.size(); i++){
				if (slots.get(i).getCourse().getName().equals(s.getName())){
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
