package com.safetynetalerts.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedicalRecord {
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("birthdate")
	private String birthDate;
	@JsonProperty("medications")
	private List<String> medications;
	@JsonProperty("allergies")
	private List<String> allergies;
}
