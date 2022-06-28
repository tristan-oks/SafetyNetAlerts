package com.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.model.PersonInFirestation;
import com.safetynetalerts.model.PersonsInFirestationWithCount;
import com.safetynetalerts.model.json.Firestation;
import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.repository.FirestationRepository;
import com.safetynetalerts.repository.PersonRepository;

@Service
public class FirestationService implements IFirestationService {
	@Autowired
	private FirestationRepository firestationRepo;
	@Autowired
	private PersonRepository personRepo;
	@Autowired
	private IMedicalRecordService medicalRecordService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<String> getFirestationAddress(int station) {
		List<String> addresses = new ArrayList<String>();
		for (Firestation firestation : firestationRepo.getFirestations()) {
			logger.info("iterate : " + firestation);
			if (firestation.getStation() == station) {
				logger.info("found : " + firestation);
				addresses.add(firestation.getAddress());
			}
		}
		return addresses;
	}

	public int getFirestationNumber(String address) {
		for (Firestation firestation : firestationRepo.getFirestations()) {
			logger.info("iterate : " + firestation);
			if (firestation.getAddress().equals(address)) {
				logger.info("found : " + firestation);
				return firestation.getStation();
			}
		}
		logger.info("Firestation Not found at address! : " + address);
		return 0;
	}

	private List<PersonInFirestation> getPersonsInFireStation(int station) {
		logger.info("get all persons covered by firestation number : " + station);

		ArrayList<PersonInFirestation> personsInFirestation = new ArrayList<PersonInFirestation>();
		List<String> stationAddressList = getFirestationAddress(station);
		logger.info("stationAddressList : " + stationAddressList);

		for (Person person : personRepo.getPersons()) {
			logger.info("iterate : " + person + ", address : " + person.getAddress());
			if (stationAddressList.contains(person.getAddress())) {
				PersonInFirestation personToAdd = new PersonInFirestation();
				personToAdd.setFirstName(person.getFirstName());
				personToAdd.setLastName(person.getLastName());
				personToAdd.setAddress(person.getAddress());
				personToAdd.setPhone(person.getPhone());
				personToAdd.setAge(medicalRecordService.getAge(person.getFirstName(), person.getLastName()));

				logger.info("personsInFirestation added : " + personToAdd.getAge() + " " + personToAdd.getFirstName()
						+ " " + personToAdd.getLastName() + " " + personToAdd.getAddress() + " "
						+ personToAdd.getPhone());
				personsInFirestation.add(personToAdd);
			}
		}
		return personsInFirestation;
	}

	public PersonsInFirestationWithCount personsInFirestationWithCount(int station) {
		PersonsInFirestationWithCount personsInFirestationWithCount = new PersonsInFirestationWithCount();
		int adults = 0;
		int childrens = 0;

		List<PersonInFirestation> personsInFirestation = getPersonsInFireStation(station);
		for (PersonInFirestation personInFirestation : personsInFirestation) {
			logger.info("countIterate : " + personInFirestation.getAge() + " " + personInFirestation.getFirstName()
					+ " " + personInFirestation.getLastName() + " " + personInFirestation.getAddress() + " "
					+ personInFirestation.getPhone());
			if (personInFirestation.getAge() < 18) {
				childrens += 1;
			} else {
				adults += 1;
			}
		}
		personsInFirestationWithCount.setPersonsInFireStation(personsInFirestation);
		personsInFirestationWithCount.setAdults(adults);
		personsInFirestationWithCount.setChildrens(childrens);
		return personsInFirestationWithCount;
	}

	public List<String> getPhoneOfPersonsInFirestation(int station) {
		List<String> phones = new ArrayList<String>();
		List<PersonInFirestation> personsInFirestation = getPersonsInFireStation(station);
		for (PersonInFirestation personInFirestation : personsInFirestation) {
			phones.add(personInFirestation.getPhone());
			logger.info("phone added : " + personInFirestation.getPhone());
		}
		return phones;
	}
}
