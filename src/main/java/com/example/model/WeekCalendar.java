package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Calendar")
public class WeekCalendar {
	
	@Id
	long id;
	
	
	
	
}
