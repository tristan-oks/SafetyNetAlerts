package com.safetynetalerts.service;

import java.util.List;
import java.util.Set;

import com.safetynetalerts.model.ChildAlert;
import com.safetynetalerts.model.FireWithStationNumber;
import com.safetynetalerts.model.Flood;
import com.safetynetalerts.model.PersonInfo;
import com.safetynetalerts.model.json.Person;

public interface IPersonService {

	public Set<String> getEmailsOfPersonsInCity(String city);

	public List<ChildAlert> getChildsAtAddressWithFamily(String address);

	public FireWithStationNumber getFireWithStationNumberAtAddress(String address);

	public List<Flood> getFlood(int[] stations);

	public List<PersonInfo> getPersonsInfo(String firstName, String lastName);

	public boolean addPerson(Person person);

	public boolean modifyPerson(Person person);

	public boolean deletePerson(String firstName, String lastName);
}
