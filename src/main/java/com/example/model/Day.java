package com.example.model;



import java.util.ArrayList;

public class Day {
    private DayName name;
    private ArrayList<Slot> slotUsed;

    public Day(DayName name){
        this.name = name;
        slotUsed = new ArrayList<Slot>();
    }
    
    public void reserveSlot(Slot data){
    	slotUsed.add(data);
    }

    /*
    public boolean reserveSlot(Slot data, ExceptionSet e){
    	//contain will check vela dew gun & room dew gun clause
    	
        if (this.slotUsed.contains(data) || !this.exceptionCheck(data, e)){
            return false;
        }
        this.slotUsed.add(data);
        return true;
    }
    */
    
    /*
    private boolean exceptionCheck(Slot data, ExceptionSet e){
    	for (Slot slot : this.slotUsed){
    		//if me vi cha tee vela trong gun law have in exception set -> fail
    		if (slot.getStartTime() == data.getStartTime() && slot.getEndTime() == data.getEndTime())
    			if (e.have(slot.getCourse())){
    				System.out.println("Subject : " + data.getCourse().getName() + " didn't pass exception case");
    			
    				return false;
    			}
    	}
    	return true;
    }
    */

    public ArrayList<Slot> getSlotUsed(){
        return this.slotUsed;
    }

    public DayName getDay(){
        return this.name;
    }

    public String toString(){
        if (this.slotUsed.size() == 0)
            return this.getDay() + " : clear day" + "\n-------------";

        String result = this.name + " : ";
        for (Slot s : slotUsed){
            result += (s.toString() + "\n");
        }
        result += "-------------";
        return result;
    }

}