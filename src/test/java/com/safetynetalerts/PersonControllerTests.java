package com.safetynetalerts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
		mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
	}

	@Test
	public void testGetChildsAtAddressWithFamily() throws Exception {
		mockMvc.perform(get("/childsAtAddress?address=947 E. Rose Dr")).andExpect(status().isOk());
	}

	@Test
	public void testGetFireWithStationNumber() throws Exception {
		mockMvc.perform(get("/fire?address=1509 Culver St")).andExpect(status().isOk());
		// .andExpect(fireWithStationNumber.getStation() == 3);
	}

	@Test
	public void testGetFlood() throws Exception {
		mockMvc.perform(get("/flood?stations=3,2,1")).andExpect(status().isOk());
	}

	@Test
	public void testGetPersonInfo() throws Exception {
		mockMvc.perform(get("/personInfo?firstName=Sophia&lastName=Zemicks")).andExpect(status().isOk());
	}
}
