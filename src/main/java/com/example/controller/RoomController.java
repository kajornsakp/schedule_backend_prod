package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.DataHandler.DataHandler;
import com.example.model.Room;

@RestController
@RequestMapping("/room")
public class RoomController {
	
	@RequestMapping(value = "/listAllRooms", method = RequestMethod.GET)
	public List<Room> listRooms(){
		return DataHandler.getAllRooms();
	}

	@RequestMapping(value = "/addRoom", method = RequestMethod.POST)
	public void createRoom(@RequestBody Room room){
		DataHandler.createRoom(room);
	}
	
	@RequestMapping(value = "/editRoom", method = RequestMethod.PUT)
	public void editRoom(@RequestBody Room room){
		DataHandler.createRoom(room);
	}
	
	@RequestMapping(value = "/removeRoom", method = RequestMethod.POST)
	public void delete(@RequestBody Room room){
		DataHandler.deleteRoom(room.getRoomName());
	}
	
	@RequestMapping(value = "/removeAll", method = RequestMethod.GET)
	public void deleteAll(){
		DataHandler.deleteAllRooms();
	}
}
