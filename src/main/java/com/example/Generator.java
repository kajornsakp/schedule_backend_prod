package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import com.example.model.Day;
import com.example.model.DayName;
import com.example.model.ExceptionSet;
import com.example.model.Room;
import com.example.model.Slot;
import com.example.model.Subject;

public class Generator {
	
	private PriorityQueue<Subject> subjects;
	private Map<DayName,Day> dayList;
	private ArrayList<Room> roomList;
	private ArrayList<ExceptionSet> exceptionSets;
	
	public Generator(){
		this.subjects = new PriorityQueue<Subject>(11, new Comparator<Subject>(){
			@Override
			public int compare(Subject o1, Subject o2) {
				
				return o1.getPriority() - o2.getPriority();
			}
		});
		this.dayList = new HashMap<DayName,Day>();
		this.roomList = new ArrayList<Room>();
		this.exceptionSets = new ArrayList<ExceptionSet>();
		
		this.dayList.put(DayName.MONDAY, new Day(DayName.MONDAY));
		this.dayList.put(DayName.TUESDAY,new Day(DayName.TUESDAY));
		this.dayList.put(DayName.WEDNESDAY,new Day(DayName.WEDNESDAY));
		this.dayList.put(DayName.THURSDAY,new Day(DayName.THURSDAY));
		this.dayList.put(DayName.FRIDAY,new Day(DayName.FRIDAY));
	
	}

	
	
	//generate room List that can fit in each subject
	private ArrayList<Room> getRoomForSubject(Subject s){
		ArrayList<Room> sortedRoom = new ArrayList<Room>();
		this.roomList.forEach(room -> {
			if (room.getCapacity() - s.getExpectedStudent() >= 0)
				sortedRoom.add(room);
		});
		
		Collections.sort(sortedRoom, new Comparator<Room>(){
			@Override
			public int compare(Room o1, Room o2) {
				return o1.getCapacity() - o2.getCapacity();
			}
		});
		
		return sortedRoom;
	}
	
	private ExceptionSet getExceptionSetOf(Subject s){
		for (ExceptionSet e : this.exceptionSets){
			if (e.have(s))
				return e;
		}
		
		//means this subject has no exception set, return empty set
		return new ExceptionSet();
	}
	
	public void addExceptionSet(ExceptionSet e){
		this.exceptionSets.add(e);
	}
	
	public void addSubject(Subject s){
		this.subjects.add(s);
	}
	
	public void addRoom(Room r){
		this.roomList.add(r);
	}
	
	
	//this will insert subject into timetable sequentially by subject's priority
	public ArrayList<Subject> SHOWTIME(){
		
		//subject that can't fit into wanted slot
		ArrayList<Subject> nokSubject = new ArrayList<Subject>();
		
		this.subjects.forEach( subject -> {
			boolean canReserve = false;
			ArrayList<Room> roomList = this.getRoomForSubject(subject); //get room that suit for that subject
			for (Room room : this.roomList){
				Slot slot = new Slot(subject.getTime()[0], subject.getTime()[1], subject, room.getRoomName());
				Day day = this.dayList.get(subject.getDay().get(0));
				
				//try to put subject on that day and that slot
				if (day.reserveSlot(slot, this.getExceptionSetOf(subject))){
					canReserve = true;
					break;
				}
			}
			if (!canReserve)
				nokSubject.add(subject);
		});
		return nokSubject;
	}
	
	public String toString(){
		String result = "";
		for (Entry<DayName, Day> entry : this.dayList.entrySet())
		{
		    result += entry.getValue().toString() + "\n";
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		Generator g = new Generator();
		g.addRoom(new Room("IC04",50));
		g.addRoom(new Room("IC06",40));
		g.addRoom(new Room("IC02",20));
		g.addRoom(new Room("IC01",20));
		
		Subject math1 = new Subject("Math1");
		Subject logic = new Subject("Logic");
		
		
		g.addSubject(math1);
		g.addSubject(logic);
		//required set prefer day and time first
		
		System.out.println(g.toString());
		//use g.SHOWTIME() to show ShubU magic
		
	}

}
