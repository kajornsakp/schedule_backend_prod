package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Lecturer;

public interface LecturerRepository extends MongoRepository<Lecturer, String>{
	public Lecturer findByLecNameIgnoreCase(String name);
}
