package com.example.solver;



import java.util.ArrayList;
import com.example.model.TimeSlot;

public interface Solver {
	void encode();
	void decode();
	ArrayList<TimeSlot> solve();
	
}
