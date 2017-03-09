package Controller;

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
    public DataManager() {
        roomList = new ArrayList<Room>();
        slotList = new ArrayList<Slot>();
        slotList.add(new Slot("09:00", "12:00"));
        slotList.add(new Slot("13:00", "16:00"));
        slotList.add(new Slot("16:30", "18:30"));

        roomList.add(new Room("IC04",50));

    }

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

}
