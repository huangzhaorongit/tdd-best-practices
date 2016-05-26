package com.taiger.examples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiger.examples.dao.FooDao;

@Service
public class FooService {

	@Autowired
	private FooDao fooDao;
	
	public String generateMessage() {
		return fooDao.getMessage();
	}

	
}
