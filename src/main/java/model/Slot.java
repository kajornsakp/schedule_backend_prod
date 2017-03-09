package model;

/**
 * Created by ShubU on 3/9/2017.
 */
public class Slot {
    private String startTime;
    private String endTime;
    private Subject course;

    //input format 18:00
    public Slot(String start, String end){
        this.course = null;
        this.startTime = start;
        this.endTime = end;

    }

    public void setCourse(Subject course){
        this.course = course;
    }

    //input in minute
    public Slot(String start, int period){

        String[] parts = start.split(":");
        int sHr = Integer.parseInt(parts[0]);
        int sMin = Integer.parseInt(parts[1]);
        sHr += (period / 60);
        sMin += (period % 60);
        if (sMin >= 60){
            sHr += 1;
            sMin -= (sMin - 60);
        }
        this.startTime = start;
        this.endTime = Integer.toString(sHr) + ":" + Integer.toString(sMin);
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object obj) {
        Slot cmp = (Slot)obj;
        if (this.getStartTime() == cmp.getStartTime() && this.getEndTime() == cmp.getEndTime())
            return true;
        return false;

    }

    @Override
    public String toString(){
        if (this.course != null)
            return this.course.getName() + " => " + this.startTime + " - " + this.endTime;
        else
            return "unknown course => " + this.startTime + " - " + this.endTime;



    }

}
