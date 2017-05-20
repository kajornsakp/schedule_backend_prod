package com.example.controller;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TimeSlot;
import com.example.repository.TimetableRepository;
import com.example.solver.SATSolver;
import com.example.solver.Solver;


@RestController
@RequestMapping("/timetable")
public class TimetableController implements Controllers<TimeSlot>{

	@Autowired
	private TimetableRepository repository;
	private Solver satSolver;
	
	@RequestMapping(value = "/generate", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<TimeSlot> generate(){
		repository.deleteAll();
		satSolver = new SATSolver();
		return repository.save(satSolver.solve());
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<TimeSlot> listAll() {
		return repository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> create(@RequestBody TimeSlot element) {
		if  (repository.findByStartTimeAndEndTimeAndDay(element.getStartTime(), element.getEndTime(), element.getDay()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Timeslot already existed!!!");
		repository.save(element);
		return ResponseEntity.ok("new time slot saved successfully!!");
	}

	@Override
	public TimeSlot update(@RequestBody TimeSlot element) {
		return repository.save(element);
	}

	@Override
	public void delete(@RequestBody TimeSlot element) {
		repository.delete(element.getId());
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}



}
