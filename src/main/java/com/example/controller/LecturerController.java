package com.example.controller;

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
import com.example.repository.LecturerRepository;


@RestController
@RequestMapping("/lec")
public class LecturerController implements Controllers<Lecturer> {
	
	@Autowired private LecturerRepository repository;
	
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
		if (repository.findByNameIgnoreCase(element.getName()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("lecturer already existed");
		element.getSubjects().forEach(subject -> {  } );
		return ResponseEntity.ok(repository.save(element));
	}

	
	@RequestMapping(method = RequestMethod.PUT)
	public Lecturer update(@RequestBody Lecturer element) {
		return repository.save(element);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody Lecturer element) {
		repository.delete(element.getId());
	}

	@RequestMapping(value = "/all", method = RequestMethod.DELETE)
	public void deleteAll() {
		repository.deleteAll();
	}
	
	
}
