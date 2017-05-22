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

import com.example.model.ExceptionSet;
import com.example.repository.ExceptionSetRepository;


@RestController
@RequestMapping("/set")
public class ExceptionSetController implements Controllers<ExceptionSet>{

	@Autowired private ExceptionSetRepository repository;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<ExceptionSet> listAll() {
		return repository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public ResponseEntity create(@RequestBody ExceptionSet element) {
		if (repository.findBySetNameIgnoreCase(element.getSetName()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("exception set already existed");
		return ResponseEntity.ok(repository.save(element));
	}

	@RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public ResponseEntity<Object> update(@RequestBody ExceptionSet element) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(element));
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json")
	public void delete(@RequestBody ExceptionSet element) {
		repository.delete(element);
	}

	@RequestMapping(value = "/all", method = RequestMethod.DELETE)
	public void deleteAll() {
		repository.deleteAll();
	}

}
