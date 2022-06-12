package com.safetynetalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import com.safetynetalerts.model.ParsedJson;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// @Autowired
		ParsedJson test = new ParsedJson();
		test.parseJSONFile("data.json");
		System.out.println("testaze");
	}

}
