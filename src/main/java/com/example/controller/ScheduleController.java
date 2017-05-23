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
		
		subject.setLecturerList((ArrayList<Lecturer>) this.mapLecturer(subject.getLecturerList(), subject));	
		ArrayList<ExceptionSet> sets = subject.getSetOn();
		sets.forEach( set -> {
			if (set != null) {
				ExceptionSet find = exRepository.findBySetNameIgnoreCase(set.getSetName());
				if (find != null) {
					ArrayList<ExceptionSet> seton = subject.getSetOn();
					seton.add(find);
					subject.setSetOn(seton);
				}
				else {
					ExceptionSet newSet = new ExceptionSet(set.getSetName());
					ArrayList<ExceptionSet> seton = subject.getSetOn();
					seton.add(find);
					subject.setSetOn(seton);
					exRepository.save(newSet);
				}
			}
		});

		repository.save(subject);
		
		return ResponseEntity.status(HttpStatus.OK).body(subject);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Subject element) {
		return ResponseEntity.status(HttpStatus.OK).body(element);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, consumes= "application/json")
	public void delete(@RequestBody Subject s){
		repository.delete(s.getId());
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.DELETE, consumes= "application/json")
	public void deleteAll(){
		repository.deleteAll();
	}
	
	private List<Lecturer> mapLecturer(ArrayList<Lecturer> lecturerList, Subject subject){
		return lecturerList.stream().map( item -> { 
			Lecturer templ = lecRepository.findByLecNameIgnoreCase(item.getLecName());
			if (templ != null) {
				ArrayList<String> subjects;
				if (templ.getSubjects() == null)
					subjects = new ArrayList<String>();
				else
					subjects = templ.getSubjects();
				subjects.add(subject.getName());
				templ.setSubjects(subjects);
				return templ;
			}
			Lecturer lecturer = new Lecturer(item.getLecName());
			ArrayList<String> subjects; 
			
			if (lecturer.getSubjects() == null)
				subjects = new ArrayList<String>();
			else
				subjects = lecturer.getSubjects();
			subjects.add(subject.getName());
			lecturer.setSubjects(subjects);
			return lecRepository.save(lecturer);
		} ).collect(Collectors.toList());
	}


	
	
	
}
