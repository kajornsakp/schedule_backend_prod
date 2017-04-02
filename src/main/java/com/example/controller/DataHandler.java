package com.example.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import com.example.config.MongoConfig;
import com.example.model.User;

public class DataHandler {

	 ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
	 MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	 
	 
	 
}
