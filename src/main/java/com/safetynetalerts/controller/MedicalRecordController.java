package com.safetynetalerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Void> addMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
		if (medicalRecordService.addMedicalRecord(medicalRecord)) {
			logger.info("medical record added : " + medicalRecord);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("medical record " + medicalRecord + " already exists, impossible to add it");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/medicalRecord")
	public ResponseEntity<Void> modifyMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
		if (medicalRecordService.modifyMedicalRecord(medicalRecord)) {
			logger.info("medical record modified : " + medicalRecord);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("sorry, impossible to modify : " + medicalRecord);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/medicalRecord")
	public ResponseEntity<Void> deleteMedicalRecord(@RequestParam(name = "firstName") final String firstName,
			@RequestParam(name = "lastName") final String lastName) {
		if (medicalRecordService.deleteMedicalRecord(firstName, lastName)) {
			logger.info("medical record of : " + firstName + " " + lastName + " deleted");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("sorry, impossible to delete medical record of : " + firstName + " " + lastName);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
