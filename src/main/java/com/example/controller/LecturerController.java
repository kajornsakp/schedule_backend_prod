package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
		if (repository.findByName(element.getName()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("lecturer already existed");
		return ResponseEntity.ok(repository.save(element));
	}

	@Override
	public Lecturer update(@RequestBody Lecturer element) {
		return repository.save(element);
	}

	@Override
	public void delete(@RequestBody Lecturer element) {
		repository.delete(element.getId());
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}
	
	
}
