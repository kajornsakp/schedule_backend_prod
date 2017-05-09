package com.example.DataHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.Generator;
import com.example.JacksonModel.TimetableWrapper;
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
	
	public static void listAllShubU(){
		Set<String> colls = mongoOperation.getCollectionNames();

		for (String s : colls) {
			System.out.println(s);
		}
	}
	public static Account findByUser(String name){
		Account user = mongoOperation.findOne(new Query(Criteria.where("username").is(name)), Account.class);
		return user;
	}

	public static Account findByUserandPassword(String name, String password){
		Account user = mongoOperation.findOne(new Query(Criteria.where("username").is(name).and("password").is(password)), Account.class);
		return user;
	}

	public static void addUser(Account user) throws IllegalAccessException {
		System.out.println( (mongoOperation.find(new Query(Criteria.where("username").is(user.getUsername())),Account.class) ) );
		mongoOperation.save(user);

	}
	
	public static Account changeAccountRole(String username, String newRole){
		Account val = mongoOperation.findAndModify(new Query(Criteria.where("username").is(username)), new Update().set("role", newRole), Account.class);
		if (val == null)
			throw new IllegalArgumentException("username not found");
		else 
			return val;
			
		
	}

	public static List<Account> deleteAllUsers(){
		mongoOperation.dropCollection("User");
		return mongoOperation.findAll(Account.class);
	}

	public static void createSubject(Subject s) {
		if (mongoOperation.find(new Query().addCriteria(Criteria.where("name").is(s.getName())), Subject.class) == null)	
			throw new IllegalArgumentException("The subject is alreadyed in the system");
		mongoOperation.save(s);
		
			
	}

	public static List<Subject> getAllSubjects(){
		return mongoOperation.findAll(Subject.class);
	}
	
	public static void deleteSubject(Subject s){
		Subject subject = mongoOperation.findOne(new Query(Criteria.where("name").is(s.getName())), Subject.class);
		if (subject == null)
			throw new IllegalArgumentException("can't find that subject");
		mongoOperation.remove(subject);
	}
	
	public static void editSubject(Subject s){
		Update update = new Update();
		update.set("name",s.getName());
		update.set("expectedStudent", s.getExpectedStudent());
		update.set("priority", s.getPriority());
		update.set("timePrefered", s.getTimePrefered());
		mongoOperation.findAndModify(new Query(Criteria.where("name").is(s.getName())),update, Subject.class);
	}
	
	public static Subject getSubjectByID(String id){
		return mongoOperation.findOne(new Query(Criteria.where("id").is(id)), Subject.class);
	}
	
	public static TimetableWrapper generateTable(){
		/*
		Generator g = new Generator();
		List<Subject> subjects = mongoOperation.findAll(Subject.class);
		List<Room> rooms = mongoOperation.findAll(Room.class);
		subjects.forEach(subject -> g.addSubject(subject));
		rooms.forEach(room -> g.addRoom(room));
		
		ArrayList<Subject> result = g.SHOWTIME();
		return result;
		*/
		
		long startTime = System.nanoTime();
		Encoder encoder = new Encoder();
        String ans = encoder.encode();
        Decoder decoder = new Decoder(encoder.getReverseTermMap());
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
        System.out.println("RUN TIME = " + duration);
        return decoder.decode(ans); 
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
	
	public static void createRoom(Room room){
		Room find = getRoom(room.getRoomName());
		if (find != null){
			Update update = new Update();
			update.set("roomName", room.getRoomName());
			update.set("capacity", room.getCapacity())
			mongoOperation.findAndModify(new Query(Criteria.where("roomName").is(room.getRoomName())), update, Room.class);
		}
			
		mongoOperation.save(room);
		
	}
	
	
	public static List<Room> getAllRooms(){
		
		return mongoOperation.findAll(Room.class);
	}
	
	public static Room getRoom(String roomName){
		return mongoOperation.findOne(new Query(Criteria.where("roomName").is(roomName)), Room.class);
	}
	
	public static Room getRoomByID(String id){
		return mongoOperation.findOne(new Query(Criteria.where("id").is(id)), Room.class);
	}

	public static void deleteRoom(String room) {
		Room find = getRoom(room);
		mongoOperation.remove(find);
	}

	public static void deleteAllRooms() {
		mongoOperation.dropCollection("Room");
		
	}
	
	


}
