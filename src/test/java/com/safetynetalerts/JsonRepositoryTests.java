package com.safetynetalerts;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynetalerts.model.json.ParsedJson;
import com.safetynetalerts.repository.JsonRepository;

@SpringBootTest
public class JsonRepositoryTests {
	@Autowired
	private JsonRepository jsonRepo;

	@Test
	public void testParsingInexistantFile() {
		ParsedJson jsonTest = jsonRepo.parseJSONFile("inexistantFile.json");
		assertNull(jsonTest);
	}
}
