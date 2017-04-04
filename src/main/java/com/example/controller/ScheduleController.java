package com.example.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.DataHandler.DataHandler;
import com.example.model.Subject;

@RestController
@RequestMapping("/scheduleAct")
public class ScheduleController {
	
	@RequestMapping(value = "/listSubjects", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Subject> getSubjects(){
		return DataHandler.getAllSubjects();
	}
	
	@RequestMapping(value = "/addSubject",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
	public void addSubject(List<Subject> s){
		DataHandler.createSubject(s);
	}
	
	@RequestMapping(value = "/removeSubject", method = RequestMethod.DELETE, consumes= "application/json")
	public void deleteSubject(Subject s){
		DataHandler.deleteSubject(s);
	}
	
	
	
}
