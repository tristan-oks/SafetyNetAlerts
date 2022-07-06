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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetEmailsOfPersonsInCity() throws Exception {
		mockMvc.perform(get("/communityEmail").param("city", "Culver")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0]", is("drk@email.com")));
	}

	@Test
	public void testGetChildsAtAddressWithFamily() throws Exception {
		mockMvc.perform(get("/childAlert").param("address", "947 E. Rose Dr")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].firstName", is("Kendrik")))
				.andExpect(jsonPath("$.[0].homeMembers.[1].firstName", is("Shawna")));
	}

	@Test
	public void testGetFireWithStationNumber() throws Exception {
		mockMvc.perform(get("/fire").param("address", "1509 Culver St")).andExpect(status().isOk())
				.andExpect(jsonPath("$.fire.[0].allergies.[0]", is("nillacilan")))
				.andExpect(jsonPath("$.stationNumber", is(3)));
	}

	@Test
	public void testGetFlood() throws Exception {
		mockMvc.perform(get("/flood/station").param("stations", "3,2,1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].address", is("1509 Culver St")));
	}

	@Test
	public void testGetPersonInfo() throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("firstName", "Sophia");
		params.add("lastName", "Zemicks");
		mockMvc.perform(get("/personInfo").params(params)).andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].age", is(34)));
	}

	@Test
	public void testSuccessfullAddPerson() throws Exception {
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"Prenom\",\"lastName\" :\"Nom\",\"address\":\"1 rue du test\","
						+ "\"city\":\"Paris\",\"zip\":\"97451\",\"phone\":\"841-874-9999\",\"email\":\"test@test.fr\"}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testFailedAddPerson() throws Exception {
		mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"John\",\"lastName\" :\"Boyd\",\"address\":\"1509 Culver St\","
						+ "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testSuccessfullModifyPerson() throws Exception {
		mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"John\",\"lastName\" :\"Boyd\",\"address\":\"1 rue du test\","
						+ "\"city\":\"Paris\",\"zip\":\"97451\",\"phone\":\"841-874-9999\",\"email\":\"test@test.fr\"}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testFailedModifyPerson() throws Exception {
		mockMvc.perform(put("/person").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"John\",\"lastName\" :\"Boyd\",\"address\":\"1509 Culver St\","
						+ "\"city\":\"Culver\",\"zip\":\"97451\",\"phone\":\"841-874-6512\",\"email\":\"jaboyd@email.com\"}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testSuccessfullDeletePerson() throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("firstName", "Sophia");
		params.add("lastName", "Zemicks");
		mockMvc.perform(delete("/person").params(params)).andExpect(status().isOk());
	}

	@Test
	public void testFailedDeletePerson() throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("firstName", "Unknown");
		params.add("lastName", "Person");
		mockMvc.perform(delete("/person").params(params)).andExpect(status().isOk());
	}
}
