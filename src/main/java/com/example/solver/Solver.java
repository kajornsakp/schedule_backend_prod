package com.example.solver;

import com.example.JacksonModel.TimetableWrapper;

public interface Solver {
	public void encode();
	public void decode();
	public TimetableWrapper solve();
	
}
