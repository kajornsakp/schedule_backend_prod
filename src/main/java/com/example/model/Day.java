package com.example.model;

import java.util.ArrayList;

public class Day {
    private DayName name;
    private ArrayList<Slot> slotUsed;

    public Day(DayName name){
        this.name = name;
        slotUsed = new ArrayList<Slot>();
    }


    public boolean reserveSlot(Slot data){
        if (this.slotUsed.contains(data))
            return false;
        this.slotUsed.add(data);
        return true;
    }

    public ArrayList<Slot> getSlotUsed(){
        return this.slotUsed;
    }

    public DayName getDay(){
        return this.name;
    }

    public String toString(){
        if (this.slotUsed.size() == 0)
            return "clear day";

        String result = "Day : " + this.name + "\n";
        for (Slot s : slotUsed){
            result += (s.toString() + "\n");
        }
        result += "-------------";
        return result;
    }

}
