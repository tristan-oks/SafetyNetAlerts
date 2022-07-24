package com.safetynetalerts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class MedicalRecordControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testSuccessfullAddMedicalRecord() throws Exception {
		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "\"firstName\":\"prenom\"," + "\"lastName\" :\"nom\"," + "\"birthdate\":\"03/06/1984\","
						+ "\"medications\":[\"bonbons:10\", \"fanta:1L\"]," + "\"allergies\":[\"fruits\", \"poisson\"]"
						+ "}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testFailedAddMedicalRecord() throws Exception {
		mockMvc.perform(post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "\"firstName\":\"John\"," + "\"lastName\" :\"Boyd\"," + "\"birthdate\":\"03/06/1984\","
						+ "\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"],"
						+ "\"allergies\":[\"nillacilan\"]" + "}"))
				.andExpect(status().is(409));
	}

	@Test
	public void testSuccessfullModifyMedicalRecord() throws Exception {
		mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "\"firstName\":\"John\"," + "\"lastName\" :\"Boyd\"," + "\"birthdate\":\"03/06/1984\","
						+ "\"medications\":[\"bonbons:10\", \"fanta:1L\"]," + "\"allergies\":[\"fruits\", \"poisson\"]"
						+ "}"))
				.andExpect(status().isOk());
	}

	@Test
	public void testFailedModifyMedicalRecord() throws Exception {
		mockMvc.perform(put("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "\"firstName\":\"John\"," + "\"lastName\" :\"Boyd\"," + "\"birthdate\":\"03/06/1984\","
						+ "\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"],"
						+ "\"allergies\":[\"nillacilan\"]" + "}"))
				.andExpect(status().is(409));
	}

	@Test
	public void testSuccessfullDeleteMedicalRecord() throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("firstName", "Sophia");
		params.add("lastName", "Zemicks");
		mockMvc.perform(delete("/medicalRecord").params(params)).andExpect(status().isOk());
	}

	@Test
	public void testFailedDeleteMedicalRecord() throws Exception {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("firstName", "Unknown");
		params.add("lastName", "Person");
		mockMvc.perform(delete("/medicalRecord").params(params)).andExpect(status().is(404));
	}

}
