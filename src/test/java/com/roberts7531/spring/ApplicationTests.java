package com.roberts7531.spring;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest(classes = Application.class)
public class ApplicationTests extends AbstractTestNGSpringContextTests {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeClass
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void addTask() throws Exception{
		MvcResult result = mockMvc.perform(post("/tasks").contentType("application/json").content("{\"taskName\":\"testtask\",\"task\":\"added for testing\"}"))
				.andExpect(status().isCreated())
				.andReturn();
		int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
		mockMvc.perform(get("/tasks/"+id)).andExpect(status().isOk())
				.andExpect(content().contentType("application/hal+json"))
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.taskName").value("testtask"))
				.andExpect(jsonPath("$.task").value("added for testing"))
		;

	}
	@Test
	public void getTask() throws Exception {
		mockMvc.perform(get("/tasks/1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/hal+json"))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.taskName").value("Task 1"))
				.andExpect(jsonPath("$.task").value("This is a test task Nr1"))
				;

	}
	@Test
	public void updateTask() throws Exception{
		MvcResult result = mockMvc.perform(post("/tasks").contentType("application/json").content("{\"taskName\":\"task to change\",\"task\":\"should be changed\"}"))
				.andExpect(status().isCreated())
				.andReturn();
		int id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
		mockMvc.perform(put("/tasks/"+id).contentType("application/json").content("{\"taskName\":\"change test\",\"task\":\"changed for testing\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.taskName").value("change test"))
				.andExpect(jsonPath("$.task").value("changed for testing"));
	}
	@Test
	public void deleteTask() throws Exception{
		mockMvc.perform(delete("/tasks/2")).andExpect(status().isOk());
		mockMvc.perform(get("/tasks/2")).andExpect(status().isNotFound());
	}
}
