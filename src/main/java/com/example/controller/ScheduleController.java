package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DataHandler.DataHandler;
import com.example.JacksonModel.SubjectWrapper;
import com.example.model.Day;
import com.example.model.Subject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/scheduleAct")
public class ScheduleController {
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void listAll(){
		DataHandler.listAllShubU();
	}
	
	@RequestMapping(value = "/listSubjects", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Subject> getSubjects(){
		return DataHandler.getAllSubjects();
	}
	
	@RequestMapping(value = "/addSubject",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public void addSubject(@RequestBody Subject s) {
		DataHandler.createSubject(s);
	}
	
	@RequestMapping(value = "/removeSubject", method = RequestMethod.DELETE, consumes= "application/json")
	public void deleteSubject(Subject s){
		DataHandler.deleteSubject(s);
	}
	
	@RequestMapping(value = "/removeAll", method = RequestMethod.DELETE, consumes= "application/json")
	public void deleteAll(){
		DataHandler.deleteAllSubjects();
	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ArrayList<Subject> generateTimetable(){
		return DataHandler.generateTable(); //return list of all subjects with subscribed time
	}
	
	
	
}
