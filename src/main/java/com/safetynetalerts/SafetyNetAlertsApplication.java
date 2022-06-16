package com.safetynetalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.safetynetalerts.model.json.FireStation;
import com.safetynetalerts.model.json.ParsedJson;
import com.safetynetalerts.repository.JsonRepository;

@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {
	@Autowired
	private JsonRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ParsedJson result = repo.parseJSONFile("data.json");
		for (FireStation fireStation : result.getFireStations()) {
			System.out.println(fireStation);
		}
	}

}
