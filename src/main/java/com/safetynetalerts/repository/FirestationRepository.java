package com.safetynetalerts.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynetalerts.model.json.Firestation;
import com.safetynetalerts.constants.Constants;

@Component
public class FirestationRepository {
	@Autowired
	private JsonRepository repo;

	public List<Firestation> getFirestations() {
		return repo.parseJSONFile(Constants.JSON_FILENAME).getFirestations();
	}

}
