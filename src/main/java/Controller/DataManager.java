package Controller;

import model.Day;
import model.DayName;
import model.Room;
import model.Slot;
import model.Subject;

import java.util.ArrayList;

/**
 * Created by ShubU on 3/9/2017.
 */
public class DataManager {

    private ArrayList<Room> roomList;
    private ArrayList<Slot> slotList;
    
    private ArrayList<Subject> subjectList;
    private ArrayList<Day> dayList;
    
    public DataManager() {
        initInstances();
    }
    
    private void initInstances()
    {
    	initRoom();
    	initSlot();
    	initSubject();
    	initDay();
    }
    
    /////////////////// initializer /////////////////////
    private void initRoom(){
    	roomList = new ArrayList<Room>();
    	roomList.add(new Room("IC04",50));
    }
    
    private void initSlot()
    {
    	slotList = new ArrayList<Slot>();
        slotList.add(new Slot("09:00", "12:00"));
        slotList.add(new Slot("13:00", "16:00"));
        slotList.add(new Slot("16:30", "18:30"));
    }

    private void initSubject()
    {
    	subjectList = new ArrayList<Subject>();
    	subjectList.add(new Subject("Algorithm"));
    }
    
    private void initDay()
    {
    	dayList = new ArrayList<Day>();
    	for (DayName d : DayName.values()) {
    	     dayList.add(new Day(d));
    	 }
    }
    ///////////////// end of initializer ///////////////////
    
    ///////////////// operation ///////////////////////
    public boolean reserveSlot(String roomName, DayName day, Slot slot, Subject course){
        if (!this.slotList.contains(slot))
            return false;

        for (Room r : roomList){
            if (r.getRoomName().equals(roomName)) {
                slot.setCourse(course);
                return r.addSlot(day, slot);
            }
        }
        return false;

    }

    public void addSlot(Slot s){
        if (!this.slotList.contains(s))
            this.slotList.add(s);
        throw new IllegalArgumentException("Slot already existed");
    }

    public ArrayList<Slot> getAvailableSlot(Room r, DayName day){
        Room room = getRoom(r);
        return room.getAvailableSlot(this.slotList, day);
    }

    private Room getRoom(Room r){
        for (Room room : roomList)
            if (room.getRoomName() == r.getRoomName())
                return room;
        throw new IllegalArgumentException("Room not existed!!");
    }
    /////////////////// end of operation ////////////////////
    /////////////////// getter /////////////////////////////
    public ArrayList<Room> getRoomList()
    {
    	return this.roomList;
    }
    
    public ArrayList<Slot> getSlotList()
    {
    	return this.slotList;
    }
    
    public ArrayList<Subject> getSubjectList()
    {
    	return this.subjectList;
    }
    
    public ArrayList<Day> getDayList()
    {
    	return this.dayList;
    }
    ////////////////// end of getter //////////////////////
    

}
