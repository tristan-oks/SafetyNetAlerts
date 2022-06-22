package com.safetynetalerts.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.model.json.MedicalRecord;
import com.safetynetalerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {
	@Autowired
	private MedicalRecordRepository repo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public int getAge(String firstName, String lastName) {
		String birthDate = getBirthDate(firstName, lastName);
		return (int) ChronoUnit.YEARS.between(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")),
				LocalDate.now());
	}

	private String getBirthDate(String firstName, String lastName) {
		for (MedicalRecord medicalRecord : repo.getMedicalRecords()) {
			logger.info("iterate : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
			if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
				logger.info("Found, birthDate is : " + medicalRecord.getBirthDate());
				return medicalRecord.getBirthDate();
			}
		}
		logger.info("Not Found!");
		return null;
	}
}