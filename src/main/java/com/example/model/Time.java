package com.example.model;

public class Time {
	private String startTime;
	private String endTime;
	
	public Time(){
	
	}
	public Time(String s, String e){
		this.startTime = s;
		this.endTime = e;
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
	public String toString(){
		return this.startTime + " - " + this.endTime;
	}
	
}
