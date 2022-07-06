package com.safetynetalerts.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerts.model.json.ParsedJson;

@Component
public class JsonRepository {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ParsedJson parseJSONFile(String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ParsedJson parsedJson = mapper.readValue(new File(filename), ParsedJson.class);
			return parsedJson;
		} catch (IOException e) {
			logger.error("Impossible to parse " + filename + "\n" + e.toString());
			// ;
			return null;
		}
	}

	public void serializeJsonToFile(ParsedJson json, String filename) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(filename).toFile(), json);
		} catch (IOException e) {
			logger.error("Impossible to write " + filename);
			e.printStackTrace();
		}
	}
}
