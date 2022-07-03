package com.safetynetalerts.service;

import java.util.List;

import com.safetynetalerts.model.json.MedicalRecord;

public interface IMedicalRecordService {

	public int getAge(String firstName, String lastName);

	public List<String> getMedications(String firstName, String lastName);

	public List<String> getAllergies(String firstName, String lastName);

	public boolean addMedicalRecord(MedicalRecord medicalRecord);

	public boolean modifyMedicalRecord(MedicalRecord medicalRecord);

	public boolean deleteMedicalRecord(String firstName, String lastName);
}
