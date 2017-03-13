package model;


import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.ArrayList;




@Entity
public class Room {
    private String roomName;
    private ArrayList<Day> days;
    private int capacity;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Room(String name, int size){
        this.roomName = name;
        this.capacity = size;
        days = new ArrayList<Day>();
        days.add(new Day(DayName.MONDAY));
        days.add(new Day(DayName.TUESDAY));
        days.add(new Day(DayName.WEDNESDAY));
        days.add(new Day(DayName.THURSDAY));
        days.add(new Day(DayName.FRIDAY));
    }

    public boolean addSlot(DayName day, Slot s){
        return this.getDay(day).reserveSlot(s);
    }

    public String getRoomName(){
        return this.roomName;
    }

    public Day getDay(DayName day){
        for (Day d : days){
            if (d.getDay().equals(day))
                return d;
        }
        throw new IllegalArgumentException("no such day");
    }

    public String toString(){
        String result = this.roomName + "\n===========";
        for (Day d : days){
            result += d.toString();
        }
        return result;
    }

    public ArrayList<Slot> getAvailableSlot(ArrayList<Slot> defaultSlot ,DayName d){
        ArrayList<Slot> clear = new ArrayList<Slot>();
        ArrayList<Slot> used = this.getDay(d).getSlotUsed();
        for (Slot s : defaultSlot) {
            if (!used.contains(s))
                clear.add(s);
        }
        return clear;
    }


}
