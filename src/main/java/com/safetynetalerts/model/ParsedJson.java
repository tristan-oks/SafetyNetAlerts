package com.safetynetalerts.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@Component
public class ParsedJson {
	@JsonProperty("persons")
	private List<Person> persons;
	@JsonProperty("firestations")
	private List<FireStation> fireStations;
	@JsonProperty("medicalrecords")
	private List<MedicalRecord> medicalRecords;

	public ParsedJson parseJSONFile(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println("ezrytzy");
			ParsedJson parsedJson = mapper.readValue(new File(fileName), ParsedJson.class);

			System.out.println(parsedJson.persons);
			System.out.println(parsedJson.fireStations);
			System.out.println(parsedJson.medicalRecords);
			return parsedJson;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
