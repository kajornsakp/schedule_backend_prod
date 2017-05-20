package com.example.model;

public enum Priority {
	LOW(5), MID(10), HIGH(15);
	private final int value;
	
	private Priority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
