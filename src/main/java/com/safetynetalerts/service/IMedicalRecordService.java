package com.safetynetalerts.service;

import java.util.List;

public interface IMedicalRecordService {

	public int getAge(String firstName, String lastName);

	public List<String> getMedications(String firstName, String lastName);

	public List<String> getAllergies(String firstName, String lastName);
}
