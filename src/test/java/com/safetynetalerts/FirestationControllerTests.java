package com.safetynetalerts;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetPhonesOfPersonsInFirestation() throws Exception {
		mockMvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0]", is("841-874-7784")));
	}

	@Test
	public void testGetPersonsInFirestationWithCount() throws Exception {
		mockMvc.perform(get("/firestation?stationNumber=3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.personsInFireStation.[0].firstName", is("John")))
				.andExpect(jsonPath("$.personsInFireStation.[1].firstName", is("Jacob")))
				.andExpect(jsonPath("$.adults", is(8))).andExpect(jsonPath("$.childrens", is(3)));
	}

	@Test
	public void testSuccessfullAddFirestation() throws Exception {
		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"1 rue du test\",\"station\":\"6\"}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFailedAddFirestation() throws Exception {
		mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"29 15th St\",\"station\":\"2\"}"))
				.andExpect(status().is(409));
	}
	
	@Test
	public void testSuccessfullModifyFirestation() throws Exception {
		mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"29 15th St\",\"station\":\"6\"}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFailedModifyFirestation() throws Exception {
		mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"1 rue du test\",\"station\":\"6\"}"))
				.andExpect(status().is(409));
	}
	
	@Test
	public void testSuccessfullDeleteFirestation() throws Exception {
		mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"29 15th St\",\"station\":\"2\"}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testFailedDeleteFirestation() throws Exception {
		mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON)
				.content("{\"address\":\"1 rue du Test\",\"station\":\"2\"}"))
				.andExpect(status().is(404));
	}
	
}
