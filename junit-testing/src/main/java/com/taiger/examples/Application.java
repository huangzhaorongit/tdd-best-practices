package com.taiger.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.taiger.examples.web.SampleController;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
	
}
