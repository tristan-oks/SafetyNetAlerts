package com.safetynetalerts.controller;

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

	@PostMapping("/medicalRecord")
	public String addMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
		if (medicalRecordService.addMedicalRecord(medicalRecord)) {
			return ("medical record added : " + medicalRecord);
		} else {
			return ("medical record " + medicalRecord + " already exists, impossible to add it");
		}
	}

	@PutMapping("/medicalRecord")
	public String modifyMedicalRecord(final MedicalRecord medicalRecord) {
		if (medicalRecordService.modifyMedicalRecord(medicalRecord)) {
			return ("medical record modified : " + medicalRecord);
		} else {
			return ("sorry, impossible to modify : " + medicalRecord);
		}
	}

	@DeleteMapping("/medicalRecord")
	public String deleteMedicalRecord(@RequestParam(name = "firstName") final String firstName,
			@RequestParam(name = "lastName") final String lastName) {
		if (medicalRecordService.deleteMedicalRecord(firstName, lastName)) {
			return ("medical record of : " + firstName + " " + lastName + " deleted");
		} else {
			return ("sorry, impossible to delete medical record of : " + firstName + " " + lastName);
		}
	}
}
