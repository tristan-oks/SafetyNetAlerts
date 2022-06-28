package com.safetynetalerts.service;

import java.util.List;

import com.safetynetalerts.model.PersonsInFirestationWithCount;

public interface IFirestationService {

	public PersonsInFirestationWithCount personsInFirestationWithCount(int station);
	
	public List<String> getPhoneOfPersonsInFirestation(int station);
	
	public int getFirestationNumber(String address);
	
}
