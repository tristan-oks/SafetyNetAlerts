package com.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.json.MedicalRecord;
import com.safetynetalerts.service.IMedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
	private IMedicalRecordService medicalRecordService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostMapping("/medicalRecord")
	public void addMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
		if (medicalRecordService.addMedicalRecord(medicalRecord)) {
			logger.info("medical record added : " + medicalRecord);
		} else {
			logger.error("medical record " + medicalRecord + " already exists, impossible to add it");
		}
	}

	@PutMapping("/medicalRecord")
	public void modifyMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
		if (medicalRecordService.modifyMedicalRecord(medicalRecord)) {
			logger.info("medical record modified : " + medicalRecord);
		} else {
			logger.error("sorry, impossible to modify : " + medicalRecord);
		}
	}

	@DeleteMapping("/medicalRecord")
	public void deleteMedicalRecord(@RequestParam(name = "firstName") final String firstName,
			@RequestParam(name = "lastName") final String lastName) {
		if (medicalRecordService.deleteMedicalRecord(firstName, lastName)) {
			logger.info("medical record of : " + firstName + " " + lastName + " deleted");
		} else {
			logger.error("sorry, impossible to delete medical record of : " + firstName + " " + lastName);
		}
	}
}
