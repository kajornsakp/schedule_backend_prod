package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Room;
import com.example.repository.RoomRepository;

@RestController
@RequestMapping("/room")
public class RoomController implements Controllers<Room> {
	
	@Autowired
	private RoomRepository repository;
	
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Room> listAll(){
		return repository.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody Room room){
		if (repository.findByRoomNameIgnoreCase(room.getRoomName()) != null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("room already existed");
		return ResponseEntity.ok(repository.save(room));
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody Room room){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(room));
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody Room room){
		repository.delete(room.getId());
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void deleteAll(){
		repository.deleteAll();
	}


	
}
