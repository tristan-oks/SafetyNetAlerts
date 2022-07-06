package com.safetynetalerts;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynetalerts.model.json.ParsedJson;
import com.safetynetalerts.repository.JsonRepository;

@SpringBootTest
public class JsonRepositoryTest {
	@Autowired
	private JsonRepository jsonRepo;

	@Test
	public void testParsingInexistantFile() {
		@SuppressWarnings("unused")
		ParsedJson jsonTest = jsonRepo.parseJSONFile("inexistantFile.json");
	}
}
