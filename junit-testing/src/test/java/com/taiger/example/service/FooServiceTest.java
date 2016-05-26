package com.taiger.example.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taiger.examples.Application;
import com.taiger.examples.service.FooService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FooServiceTest {

	@Autowired
	private FooService fooService;

	@Test
	public void testGenerateMessage() {
		
		String message = fooService.generateMessage();
		assertThat(message, equalTo("Hello World!"));

	}
}
