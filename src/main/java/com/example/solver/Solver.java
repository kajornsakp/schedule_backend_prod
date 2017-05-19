package com.example.solver;

import com.example.JacksonModel.TimetableWrapper;

public interface Solver {
	void encode();
	void decode();
	public TimetableWrapper solve();
	
}
