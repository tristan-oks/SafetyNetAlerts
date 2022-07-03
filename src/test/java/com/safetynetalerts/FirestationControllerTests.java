package com.safetynetalerts;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetPhonesOfPersonsInFirestation() throws Exception {
		mockMvc.perform(get("/phoneAlert?firestation=1")).andExpect(status().isOk());
	}

	@Test
	public void testGetPersonsInFirestationWithCount() throws Exception {
		mockMvc.perform(get("/firestation?stationNumber=3")).andExpect(status().isOk())
				.andExpect(jsonPath("$.personsInFireStation.[0].firstName", is("John")))
				.andExpect(jsonPath("$.personsInFireStation.[1].firstName", is("Jacob")))
				.andExpect(jsonPath("$.adults", is(8)))
				.andExpect(jsonPath("$.childrens", is(3)));
	}
}
