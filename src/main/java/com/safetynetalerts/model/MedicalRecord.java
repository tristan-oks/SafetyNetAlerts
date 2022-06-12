package com.safetynetalerts.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Component
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
