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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.TimeSlot;
import com.example.repository.RoomRepository;
import com.example.repository.ScheduleRepository;
import com.example.repository.TimetableRepository;
import com.example.solver.SATSolver;
import com.example.solver.Solver;


@RestController
@RequestMapping("/timetable")
public class TimetableController implements AccessController<TimeSlot>{

	@Autowired
	private TimetableRepository repository;
	@Autowired
	private ScheduleRepository courseRepository;
	@Autowired
	private RoomRepository roomRepository;
	private Solver satSolver;
	
	@RequestMapping(value = "/generate", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<TimeSlot> generate(){
		repository.deleteAll();
		satSolver = new SATSolver(roomRepository.findAll(),courseRepository.findAll());
		return repository.save(satSolver.solve());
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<TimeSlot> listAll() {
		return repository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	//input lecturer id
	public List<TimeSlot> listFor(@RequestParam("id") String id){
		List<TimeSlot> allSlots = repository.findAll();
		return allSlots.stream().filter(slot -> slot.getSubject().getLecturerList().contains(id)).collect(Collectors.toList());
	}

	@RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> create(@RequestBody TimeSlot element) {
		if  (repository.findByStartTimeAndEndTimeAndDay(element.getStartTime(), element.getEndTime(), element.getDay()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Timeslot already existed!!!");
		repository.save(element);
		return ResponseEntity.ok("new time slot saved successfully!!");
	}

	@RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Object> update(@RequestBody TimeSlot element) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(element));
	}

	@RequestMapping(method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void delete(@RequestBody TimeSlot element) {
		repository.delete(element.getId());
		
	}

	@RequestMapping(value = "/all", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteAll() {
		repository.deleteAll();
		
	}

	

}
