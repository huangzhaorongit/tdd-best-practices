package com.taiger.examples.web;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.taiger.examples.Application;
import com.taiger.examples.elasticsearch.ElasticsearchTestNode;
import com.taiger.examples.service.FooService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SampleControllerServiceElasticsearchTest {

	@Autowired
	private FooService fooService;

	@Rule
	public ElasticsearchTestNode testNode = new ElasticsearchTestNode();

	private MockMvc restMockMvc;

	@Autowired
	private SampleController sampleController;

	protected ResultActions actions;

	@Before
	public void setUp() throws Throwable {
		this.restMockMvc = MockMvcBuilders.standaloneSetup(sampleController).build();
		testNode.before();
		fooService.setClient(testNode.getClient());
	}

	@Test
	public void search() throws Exception {
		actions = restMockMvc.perform(get("/search").accept(MediaType.APPLICATION_JSON));
		
		actions.andDo(print()).andExpect(status().isOk());
		assertNotNull(actions.andReturn().getResponse().getContentAsString());
		assertFalse(actions.andReturn().getResponse().getContentAsString().isEmpty());
	}
	

}
