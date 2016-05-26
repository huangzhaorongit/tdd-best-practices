package com.taiger.examples.dao;

import org.springframework.stereotype.Repository;

@Repository
public class FooDao {
	
	public String getMessage() {
		return "Hello World!";
	}
}
