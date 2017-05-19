package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface Controllers<T> {
	public List<T> listAll();
	public ResponseEntity<Object> create(T element);
	public T update(T element);
	public void delete(T element);
	public void deleteAll();
}
