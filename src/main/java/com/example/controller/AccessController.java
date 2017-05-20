package com.example.controller;

import java.util.List;

public interface AccessController<T> extends Controllers<T>{
	public List<T> listFor(String id);
}
