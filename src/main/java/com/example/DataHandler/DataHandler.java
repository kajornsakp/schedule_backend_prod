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
import com.example.model.Lecturer;
import com.example.model.Room;
import com.example.model.Subject;
import com.example.model.Account;

public class DataHandler {
	static ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
	static MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");

	public static List<Account> getAllUsers(){
		return mongoOperation.findAll(Account.class);
	}

	public static Account findByUser(String name){
		Account user = mongoOperation.findOne(new Query(Criteria.where("username").is(name)), Account.class);
		return user;
	}

	public static boolean addUser(Account user){
		try{
			mongoOperation.save(user);
			return true;
		} catch (Exception e){
			return false;
		}
	}

	public static List<Account> deleteAllUsers(){
		mongoOperation.dropCollection("User");
		return mongoOperation.findAll(Account.class);
	}

	public static void createSubject(Subject s) {
		mongoOperation.save(s);
	}

	public static List<Subject> getAllSubjects(){
		return mongoOperation.findAll(Subject.class);
	}
	
	public static void deleteSubject(Subject s){
		Subject subject = mongoOperation.findOne(new Query(Criteria.where("name").is(s.getName())), Subject.class);
		mongoOperation.remove(subject);
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
	
	public static void deleteAllSubjects(){
		mongoOperation.dropCollection("Subject");
	
	}
	
	public static List<Lecturer> getAllLecturer(){
		return mongoOperation.findAll(Lecturer.class);
	}
	
	public static Lecturer findLecturerByName(String name){
		return mongoOperation.findOne(new Query(Criteria.where("name").is(name)), Lecturer.class);
	}

	public static void createLecturer(Lecturer lecturer) {
		mongoOperation.save(lecturer);
	}
	
	


}
