package com.safetynetalerts.service;

import java.util.List;

import com.safetynetalerts.model.ChildAlert;
import com.safetynetalerts.model.FireWithStationNumber;
import com.safetynetalerts.model.Flood;
import com.safetynetalerts.model.PersonAtAddressWithMedicalRecords;

public interface IPersonService {

	public List<String> getEmailsOfPersonsInCity(String city);

	public List<ChildAlert> getChildsAtAddressWithFamily(String address);

	public FireWithStationNumber getFireWithStationNumberAtAddress(String address);
	
	public List<PersonAtAddressWithMedicalRecords> getPersonsAtAddressWithMedicalRecords(String address);
	
	public List<Flood> getFlood(int station);
}
