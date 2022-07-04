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
public class PersonControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetEmailsOfPersonsInCity() throws Exception {
		mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk())
		.andExpect(jsonPath("$.[0]", is("jaboyd@email.com")));
	}

	@Test
	public void testGetChildsAtAddressWithFamily() throws Exception {
		mockMvc.perform(get("/childsAtAddress?address=947 E. Rose Dr")).andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].firstName", is("Kendrik")))
		.andExpect(jsonPath("$.[0].homeMembers.[1].firstName", is("Shawna")));
	}

	@Test
	public void testGetFireWithStationNumber() throws Exception {
		mockMvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk())
		.andExpect(jsonPath("$.fire.[0].allergies.[0]", is("nillacilan")))
		.andExpect(jsonPath("$.stationNumber", is(3)));
	}

	@Test
	public void testGetFlood() throws Exception {
		mockMvc.perform(get("/flood?stations=3,2,1")).andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].address", is("1509 Culver St")));
	}

	@Test
	public void testGetPersonInfo() throws Exception {
		mockMvc.perform(get("/personInfo?firstName=Sophia&lastName=Zemicks")).andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].age", is(34)));
	}
}
