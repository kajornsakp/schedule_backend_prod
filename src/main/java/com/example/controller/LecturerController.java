package com.example.controller;

import java.lang.annotation.ElementType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Lecturer;
import com.example.model.Subject;
import com.example.repository.LecturerRepository;
import com.example.repository.ScheduleRepository;


@RestController
@RequestMapping("/lec")
public class LecturerController implements Controllers<Lecturer> {
	
	@Autowired private LecturerRepository repository;
	@Autowired private ScheduleRepository courseRepository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Lecturer> listAll() {
		return repository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Lecturer getItem(@RequestBody Lecturer lec) {
		return repository.findOne(lec.getId());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Lecturer element) {
		if (repository.findByLecNameIgnoreCase(element.getLecName()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("lecturer already existed");
		if (!this.courseExist(element))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("subject does not existed");
		
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(element));
	}

	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Lecturer element) {
		if (!this.courseExist(element))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("subject does not existed");
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(element));
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody Lecturer element) {
		repository.delete(element.getId());
	}

	@RequestMapping(value = "/all", method = RequestMethod.DELETE)
	public void deleteAll() {
		repository.deleteAll();
	}
	
	private boolean courseExist(Lecturer element) {
		System.out.println("test course eixst 1 :" + element);
		if (element.getSubjects() == null)
			return true;
		for (int i = 0 ; i < element.getSubjects().size(); i++) {
			Subject s = courseRepository.findByNameIgnoreCase(element.getSubjects().get(i));
			
			if (s == null)
				return false;
		}
		return true;
	}
	
	
}
