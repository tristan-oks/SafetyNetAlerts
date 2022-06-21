package com.safetynetalerts.model.json;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public List<String> getMedications() {
		return medications;
	}

	public void setMedications(List<String> medications) {
		this.medications = medications;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	public int getAge() {
	return (int) ChronoUnit.YEARS.between(LocalDate.parse(birthDate,DateTimeFormatter.ofPattern("mm/dd/yyyy")), LocalDate.now());
	}
}
