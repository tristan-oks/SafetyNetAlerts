package com.safetynetalerts.model.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ParsedJson {
	@JsonProperty("persons")
	private List<Person> persons;
	@JsonProperty("firestations")
	private List<FireStation> fireStations;
	@JsonProperty("medicalrecords")
	private List<MedicalRecord> medicalRecords;

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public List<FireStation> getFireStations() {
		return fireStations;
	}

	public void setFireStations(List<FireStation> fireStations) {
		this.fireStations = fireStations;
	}

	public List<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}
}
