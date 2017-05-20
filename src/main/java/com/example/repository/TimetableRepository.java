package com.example.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.DayName;
import com.example.model.Subject;
import com.example.model.TimeSlot;

public interface TimetableRepository extends MongoRepository<TimeSlot, String>{
	public TimeSlot findBySubject(Subject s);
	public TimeSlot findByStartTimeAndEndTimeAndDay(String startTime, String endTime, DayName day);
}
