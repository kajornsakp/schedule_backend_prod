package com.example.repository;

import com.example.model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Subject, String> {
	public Subject findByNameIgnoreCase(String name);
}
