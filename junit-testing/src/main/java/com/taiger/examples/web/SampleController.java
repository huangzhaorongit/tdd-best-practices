package com.taiger.examples.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taiger.examples.service.FooService;

@Controller
public class SampleController {
	@Autowired
	private FooService foo;

	@RequestMapping("/foo")
	@ResponseBody
	public String home() {
		return foo.generateMessage();
	}

	@RequestMapping("/search")
	@ResponseBody
	public List<String> search() {
		return foo.search();
	}
	
}
