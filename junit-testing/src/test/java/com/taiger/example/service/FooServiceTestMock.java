package com.taiger.example.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taiger.examples.Application;
import com.taiger.examples.dao.FooDao;
import com.taiger.examples.service.FooService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class FooServiceTestMock {
	
	@Mock
	private FooDao fooDaoMock;

	@InjectMocks
	@Autowired
	private FooService fooService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGenerateMessage() {
		// specify mock behave when method called
		when(fooDaoMock.getMessage()).thenReturn("Hello World with Mock!!");

		String message = fooService.generateMessage();
		assertThat(message, equalTo("Hello World with Mock!!"));

	}
}
