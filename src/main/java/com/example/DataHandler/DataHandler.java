package com.example.DataHandler;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.config.MongoConfig;
import com.example.model.Subject;
import com.example.model.User;

public class DataHandler {
	static ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
	static MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");

	public static List<User> getAllUsers(){
		return mongoOperation.findAll(User.class);
	}

	public static List<User> findByUser(String name){
		List<User> users = mongoOperation.find(new Query(Criteria.where("username").is(name)), User.class);
		return users;
	}

	public static boolean addUser(User user){
		try{
			mongoOperation.save(user);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	public static List<User> deleteAllUsers(){
		mongoOperation.dropCollection("User");
		return mongoOperation.findAll(User.class);
	}

	public static void createSubject(List<Subject> s) {
		s.forEach((subject) -> mongoOperation.save(subject));
	}

	public static List<Subject> getAllSubjects(){
		return mongoOperation.findAll(Subject.class);
	}
	
	public static void deleteSubject(Subject s){
		mongoOperation.remove(s);
	}
	
	public static void editSubject(Subject s){
		Subject temp = mongoOperation.findOne(new Query(Criteria.where("id").is(s.getId())), Subject.class);
		
	}
	
	


}
