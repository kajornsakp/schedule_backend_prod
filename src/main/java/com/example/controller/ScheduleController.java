package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.ExceptionSet;
import com.example.model.Lecturer;
import com.example.model.Subject;
import com.example.repository.ExceptionSetRepository;
import com.example.repository.LecturerRepository;
import com.example.repository.ScheduleRepository;


@RestController
@RequestMapping("/scheduleAct")
public class ScheduleController implements AccessController<Subject>{
	
	@Autowired
	private ScheduleRepository repository;
	
	@Autowired
	private LecturerRepository lecRepository;
	
	@Autowired
	private ExceptionSetRepository exRepository;

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Subject> listAll(){
		return repository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Subject> listFor(@RequestParam("id") String id) {
		return repository.findAll().stream().filter(course -> course.getLecturerList().contains(id)).collect(Collectors.toList());
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public ResponseEntity<Object> create(@RequestBody Subject subject) {
	
		
		
		if (repository.findByNameIgnoreCase(subject.getName()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Course already existed!!!");
		
		subject.setLecturerList((ArrayList<Lecturer>) subject.getLecturerList().stream().map( item -> { 
			System.out.println("check lec name : " + item.getLecName());
			Lecturer templ = lecRepository.findByLecNameIgnoreCase(item.getLecName());
			System.out.println("check l : " + templ.getLecName());
			if (templ != null)
				return templ;
			return lecRepository.save(new Lecturer(item.getLecName()));
		} ).collect(Collectors.toList()));
		System.out.println(subject.getLecturerList());
		
		ExceptionSet set = subject.getSetOn();
		if (set != null) {
			ExceptionSet find = exRepository.findBySetNameIgnoreCase(set.getSetName());
			if (find != null)
				subject.setSetOn(find);
			else {
				ExceptionSet newSet = new ExceptionSet(set.getSetName());
				subject.setSetOn(newSet);
				exRepository.save(newSet);
			}
				
		}
		
		System.out.println("chekc lec list 2 : " + subject);
		repository.save(subject);
		
		return ResponseEntity.ok("");
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
