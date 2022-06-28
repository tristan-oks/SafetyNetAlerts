package com.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.model.Child;
import com.safetynetalerts.model.ChildAlert;
import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.repository.PersonRepository;

@Service
public class PersonService implements IPersonService {
	@Autowired
	private PersonRepository repo;
	@Autowired
	private IMedicalRecordService medicalRecordService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public List<String> getEmailsOfPersonsInCity(String city) {
		List<String> emails = new ArrayList<String>();
		logger.info("emails city : " + city);

		for (Person person : repo.getPersons()) {
			logger.info("iterate : " + person + ", city : " + person.getCity());
			if (person.getCity().equals(city)) {
				logger.info("added : " + person);
				emails.add(person.getEmail());
			}
		}
		return emails;
	}

	private List<Person> getPersonsAtAddress(String address) {
		List<Person> personsAtAddress = new ArrayList<Person>();
		for (Person person : repo.getPersons()) {
			logger.info("iterate : " + person);
			if (person.getAddress().equals(address)) {
				personsAtAddress.add(person);
			}
		}
		return personsAtAddress;
	}

	private List<Child> getChildsAtAddress(String address) {
		List<Child> childsAtAddress = new ArrayList<Child>();

		List<Person> personsAtAddress = getPersonsAtAddress(address);
		for (Person person : personsAtAddress) {
			int age = medicalRecordService.getAge(person.getFirstName(), person.getLastName());
			if (age < 18) {
				Child child = new Child();
				logger.info("iterate : " + person);
				child.setFirstName(person.getFirstName());
				child.setLastName(person.getLastName());
				child.setAge(age);
				logger.info("added child : " + child);
				childsAtAddress.add(child);
			}
		}
		return childsAtAddress;
	}
	
	public List<ChildAlert> getChildsAtAddressWithFamily(String address) {
		List<Child> childsAtAddress = getChildsAtAddress(address);
		List<ChildAlert> childsAtAddressWithFamily = new ArrayList<ChildAlert>();
		for (Child child : childsAtAddress) {
			ChildAlert childAlertToAdd = new ChildAlert();
			childAlertToAdd.setFirstName(child.getFirstName());
			childAlertToAdd.setLastName(child.getLastName());
			childAlertToAdd.setAge(child.getAge());
			childAlertToAdd.setHomeMembers(getPersonsAtAddress(address));
			childsAtAddressWithFamily.add(childAlertToAdd);
		}
		return childsAtAddressWithFamily;
	}
}