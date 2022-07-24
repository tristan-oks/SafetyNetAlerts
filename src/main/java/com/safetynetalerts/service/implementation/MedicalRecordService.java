package com.safetynetalerts.service.implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.constants.Constants;
import com.safetynetalerts.model.json.MedicalRecord;
import com.safetynetalerts.model.json.ParsedJson;
import com.safetynetalerts.repository.JsonRepository;
import com.safetynetalerts.repository.MedicalRecordRepository;
import com.safetynetalerts.service.IMedicalRecordService;

@Service
public class MedicalRecordService implements IMedicalRecordService {
	@Autowired
	private JsonRepository jsonRepo;
	@Autowired
	private MedicalRecordRepository repo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public int getAge(String firstName, String lastName) {
		logger.info("calculate age of : " + firstName + " " + lastName);
		String birthDate = getBirthDate(firstName, lastName);
		return (int) ChronoUnit.YEARS.between(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("MM/dd/yyyy")),
				LocalDate.now());
	}

	private String getBirthDate(String firstName, String lastName) {
		logger.info("get birthdate of : " + firstName + " " + lastName);
		for (MedicalRecord medicalRecord : repo.getMedicalRecords()) {
			logger.trace("iterate : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
			if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
				logger.info("Found, birthDate is : " + medicalRecord.getBirthdate());
				return medicalRecord.getBirthdate();
			}
		}
		logger.info("Not Found!");
		return null;
	}

	public List<String> getMedications(String firstName, String lastName) {
		logger.info("get medical records of : " + firstName + " " + lastName);
		for (MedicalRecord medicalRecord : repo.getMedicalRecords()) {
			logger.trace("iterate : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
			if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
				logger.info("Found, medications are : " + medicalRecord.getMedications());
				return medicalRecord.getMedications();
			}
		}
		logger.info("Not Found!");
		return null;
	}

	public List<String> getAllergies(String firstName, String lastName) {
		logger.info("get allergies of : " + firstName + " " + lastName);
		for (MedicalRecord medicalRecord : repo.getMedicalRecords()) {
			logger.trace("iterate : " + medicalRecord.getFirstName() + " " + medicalRecord.getLastName());
			if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
				logger.info("Found, allergies are : " + medicalRecord.getAllergies());
				return medicalRecord.getAllergies();
			}
		}
		logger.info("Not Found!");
		return null;
	}

	public boolean addMedicalRecord(MedicalRecord medicalRecord) {
		logger.info("medical record to add : " + medicalRecord);
		List<MedicalRecord> medicalRecords = repo.getMedicalRecords();
		for (MedicalRecord medicalRecordLoop : medicalRecords) {
			if ((medicalRecordLoop.getFirstName().equals(medicalRecord.getFirstName()))
					&& (medicalRecordLoop.getLastName().equals(medicalRecord.getLastName()))
					&& (medicalRecordLoop.getBirthdate().equals(medicalRecord.getBirthdate()))
					&& (medicalRecordLoop.getMedications().equals(medicalRecord.getMedications()))
					&& (medicalRecordLoop.getAllergies().equals(medicalRecord.getAllergies()))) {
				logger.info("medical record : " + medicalRecord + "already exists");
				return false;
			}
		}
		medicalRecords.add(medicalRecord);
		logger.info("resulted medical records : " + medicalRecords);
		ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
		json.setMedicalRecords(medicalRecords);
		jsonRepo.serializeJsonToFile(json, "Constants.RESULT_FILENAME");
		return true;
	}

	public boolean modifyMedicalRecord(MedicalRecord medicalRecord) {
		logger.info("medical record to modify : " + medicalRecord);
		boolean modified = false;
		List<MedicalRecord> modifiedMedicalRecords = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecordLoop : repo.getMedicalRecords()) {
			if ((medicalRecordLoop.getFirstName().equals(medicalRecord.getFirstName()))
					&& (medicalRecordLoop.getLastName().equals(medicalRecord.getLastName()))
					&& (!((medicalRecordLoop.getBirthdate().equals(medicalRecord.getBirthdate()))
							&& (medicalRecordLoop.getMedications().equals(medicalRecord.getMedications()))
							&& (medicalRecordLoop.getAllergies().equals(medicalRecord.getAllergies()))))) {
				medicalRecordLoop = medicalRecord;
				modified = true;
			}
			logger.info("medical record : " + medicalRecordLoop + "modified");
			modifiedMedicalRecords.add(medicalRecordLoop);
		}
		if (modified) {
			logger.info("resulted medical record : " + modifiedMedicalRecords);
			ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
			json.setMedicalRecords(modifiedMedicalRecords);
			jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		}
		return modified;
	}

	public boolean deleteMedicalRecord(String firstName, String lastName) {
		logger.info("person to delete medical record : " + firstName + lastName);
		boolean deleted = false;
		List<MedicalRecord> modifiedMedicalRecords = new ArrayList<MedicalRecord>();
		for (MedicalRecord medicalRecordLoop : repo.getMedicalRecords()) {
			if (medicalRecordLoop.getFirstName().equals(firstName)
					&& medicalRecordLoop.getLastName().equals(lastName)) {
				deleted = true;
				logger.info("medical record of : " + medicalRecordLoop + "deleted");
			} else {
				modifiedMedicalRecords.add(medicalRecordLoop);
			}
		}
		if (deleted) {
			logger.info("resulted medical records : " + modifiedMedicalRecords);
			ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
			json.setMedicalRecords(modifiedMedicalRecords);
			jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		}
		return deleted;
	}
}