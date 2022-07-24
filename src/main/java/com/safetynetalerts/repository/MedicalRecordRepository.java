package com.safetynetalerts.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynetalerts.constants.Constants;
import com.safetynetalerts.model.json.MedicalRecord;

@Component
public class MedicalRecordRepository {
	@Autowired
	private JsonRepository repo;

	public List<MedicalRecord> getMedicalRecords() {
		return repo.parseJSONFile(Constants.JSON_FILENAME).getMedicalRecords();
	}
}