package com.safetynetalerts.service;

import java.util.List;
import java.util.Set;

import com.safetynetalerts.model.PersonsInFirestationWithCount;
import com.safetynetalerts.model.json.Firestation;

public interface IFirestationService {

	public PersonsInFirestationWithCount personsInFirestationWithCount(int station);
	
	public Set<String> getPhoneOfPersonsInFirestation(int station);
	
	public int getFirestationNumber(String address);
	
	public List<String> getFirestationAddress(int station);

	public boolean addFirestation(Firestation firestation);
	
	public boolean modifyFirestation(Firestation firestation);
	
	public boolean deleteFirestation(Firestation firestation);
	
}
