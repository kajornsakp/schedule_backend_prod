package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.model.Subject;

public interface ScheduleRepository extends MongoRepository<Subject, String> {
	public Subject findByName(String name);
}
