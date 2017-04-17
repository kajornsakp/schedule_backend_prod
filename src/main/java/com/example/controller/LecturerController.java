package com.example.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.DataHandler.DataHandler;
import com.example.model.Lecturer;

@RestController
@RequestMapping("/lec")
public class LecturerController {
	@RequestMapping(value = "/listLecturer", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Lecturer> getAllLecturer(){
		return DataHandler.getAllLecturer();
	}
	
	@RequestMapping(value = "/getLecturer", method = RequestMethod.OPTIONS, produces = {MediaType.APPLICATION_JSON_VALUE})
	public Lecturer getLecturer(String name){
		return DataHandler.findLecturerByName(name);
	}
	
	@RequestMapping(value = "/addLecturer", method = RequestMethod.OPTIONS, consumes= "application/json")
	public void createLecturer(Lecturer lecturer){
		DataHandler.createLecturer(lecturer);
	}
	
}
