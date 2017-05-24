package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "TimeSlot")
public class TimeSlot {
	@Id
	private String id;
	private Subject subject;
	private Room room;
	private DayName day;
	private String startTime;
	private String endTime;
	
	public TimeSlot() {
		
	}

	public TimeSlot(Subject subject){
		this.subject = subject;
	}
	
	public DayName getDay() {
		return day;
	}

	public void setDay(DayName day) {
		this.day = day;
	}

	public String getId() {
		return id;
	}
	
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public boolean equals(Object obj) {
		Subject s= (Subject) obj;
		return this.subject.getId().equals(s.getId());
	}
}
