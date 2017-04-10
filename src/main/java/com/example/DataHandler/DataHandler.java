package com.example.DataHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.Generator;
import com.example.config.MongoConfig;
import com.example.model.Day;
import com.example.model.Room;
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

	public static void createSubject(Subject s) {
		mongoOperation.save(s);
	}

	public static List<Subject> getAllSubjects(){
		return mongoOperation.findAll(Subject.class);
	}
	
	public static void deleteSubject(Subject s){
		mongoOperation.remove(s);
	}
	
	public static void editSubject(Subject s){
		Update update = new Update();
		update.set("name",s.getName());
		update.set("expectedStudent", s.getExpectedStudent());
		update.set("priority", s.getPriority());
		update.set("subscribeDay", s.getSubscribeDay());
		update.set("subscribeTime", s.getSubscribeTime());
		update.set("timePrefered", s.getTimePrefered());
		mongoOperation.findAndModify(new Query(Criteria.where("name").is(s.getName())),update, Subject.class);
	}
	
	public static ArrayList<Subject> generateTable(){
		Generator g = new Generator();
		List<Subject> subjects = mongoOperation.findAll(Subject.class);
		List<Room> rooms = mongoOperation.findAll(Room.class);
		subjects.forEach(subject -> g.addSubject(subject));
		rooms.forEach(room -> g.addRoom(room));
		
		ArrayList<Subject> result = g.SHOWTIME();
		return result;
	}
	
	


}
