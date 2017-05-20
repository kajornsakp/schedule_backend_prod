package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ExceptionSet;
import com.example.model.Subject;
import com.example.repository.ScheduleRepository;


@RestController
@RequestMapping("/scheduleAct")
public class ScheduleController implements AccessController<Subject>{
	
	@Autowired
	private ScheduleRepository repository;

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Subject> listAll(){
		return repository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Subject> listFor(String id) {
		return repository.findAll().stream().filter(course -> course.getLecturerList().contains(id)).collect(Collectors.toList());
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public ResponseEntity<Object> create(@RequestBody Subject s) {
		if (repository.findByNameIgnoreCase(s.getName()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course already existed!!!");
		repository.save(s);
		return ResponseEntity.ok("course added");
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Subject update(@RequestBody Subject element) {
		return repository.save(element);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, consumes= "application/json")
	public void delete(@RequestBody Subject s){
		repository.delete(s.getId());
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.DELETE, consumes= "application/json")
	public void deleteAll(){
		repository.deleteAll();
	}

	
	
}
