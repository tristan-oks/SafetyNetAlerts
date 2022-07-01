package com.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.model.json.ParsedJson;

@Component
public class JsonRepository {

	public ParsedJson parseJSONFile(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ParsedJson parsedJson = mapper.readValue(new File(fileName), ParsedJson.class);
			return parsedJson;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void serializeJsonToFile(ParsedJson json, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(filename).toFile(), json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
