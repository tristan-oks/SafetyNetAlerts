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
public class FirestationService {
	@Autowired
	private FirestationRepository firestationRepo;
	@Autowired
	private PersonRepository personRepo;
	@Autowired
	private MedicalRecordService medicalRecordService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public boolean belongToFirestation(int stationNumber) {

		for (Firestation firestation : firestationRepo.getFirestations()) {
			logger.info("iterate : " + firestation + ", station : " + firestation.getStation());
			if (firestation.getStation() == stationNumber) {
				logger.info("found : " + firestation);
				return true;
			}
		}
		return false;
	}
	
	public boolean belongToFirestation(String address) {

		for (Firestation firestation : firestationRepo.getFirestations()) {
			logger.info("iterate : " + firestation + ", stationAddress : " + firestation.getAddress());
			if (firestation.getAddress().equals(address)) {
				logger.info("found : " + firestation);
				return true;
			}
		}
		return false;
	}


	public String getFirestationAddress(int station) {
		for (Firestation firestation : firestationRepo.getFirestations()) {
			logger.info("iterate : " + firestation );
			if (firestation.getStation() == station) {
				logger.info("found : " + firestation);
				return firestation.getAddress();
			}
		}
		logger.info("firestation " + station + "Not Found !");
		return null;
	}
	
	public List<PersonInFirestation> getPersonsInFireStation(int station) {
		logger.info("get all persons covered by firestation number : " + station);

		List<PersonInFirestation> personsInFirestation = new ArrayList<PersonInFirestation>();
		PersonInFirestation personToAdd = new PersonInFirestation();
		//FirestationService firestationService = new FirestationService();
		//MedicalRecordService medicalRecordService = new MedicalRecordService();
		String stationAddress = getFirestationAddress(station);
		logger.info("stationAddress : " + stationAddress);

		for (Person person : personRepo.getPersons()) {
			logger.info("iterate : " + person + ", address : " + person.getAddress());
			if (person.getAddress().equals(stationAddress)) {
				personToAdd.setFirstName(person.getFirstName());
				personToAdd.setLastName(person.getLastName());
				personToAdd.setAddress(person.getAddress());
				personToAdd.setPhone(person.getPhone());
				personToAdd.setAge(medicalRecordService.getAge(person.getFirstName(), person.getLastName()));

				personsInFirestation.add(personToAdd);
			}
		}
		return personsInFirestation;
	}

	public PersonsInFirestationWithCount personsInFirestationWithCount(List<PersonInFirestation> personsInFirestation) {
		PersonsInFirestationWithCount personsInFirestationWithCount = new PersonsInFirestationWithCount();
		int adults = 0;
		int childrens = 0;

		for (PersonInFirestation personInFirestation : personsInFirestation) {
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
}
