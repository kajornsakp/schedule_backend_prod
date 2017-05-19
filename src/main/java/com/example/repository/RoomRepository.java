package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Room;

public interface RoomRepository extends MongoRepository<Room, String>{
	public Room findByRoomName(String name);
}
