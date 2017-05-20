package com.example.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.ExceptionSet;

public interface ExceptionSetRepository extends MongoRepository<ExceptionSet, String> {
	public ExceptionSet findBySetName(String name);

}
