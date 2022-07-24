package com.safetynetalerts.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.constants.Constants;
import com.safetynetalerts.model.PersonInFirestation;
import com.safetynetalerts.model.PersonsInFirestationWithCount;
import com.safetynetalerts.model.json.Firestation;
import com.safetynetalerts.model.json.ParsedJson;
import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.repository.FirestationRepository;
import com.safetynetalerts.repository.JsonRepository;
import com.safetynetalerts.repository.PersonRepository;
import com.safetynetalerts.service.IFirestationService;
import com.safetynetalerts.service.IMedicalRecordService;

@Service
public class FirestationService implements IFirestationService {

	@Autowired
	private JsonRepository jsonRepo;
	@Autowired
	private FirestationRepository firestationRepo;
	@Autowired
	private PersonRepository personRepo;
	@Autowired
	private IMedicalRecordService medicalRecordService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public List<String> getFirestationAddress(int station) {
		logger.info("get addresses covered by firestation n° : " + station);
		List<String> addresses = new ArrayList<String>();
		for (Firestation firestation : firestationRepo.getFirestations()) {
			logger.trace("iterate : " + firestation);
			if (firestation.getStation() == station) {
				logger.info("found : " + firestation);
				addresses.add(firestation.getAddress());
			}
		}
		logger.info("addresses found : " + addresses);
		return addresses;
	}

	public int getFirestationNumber(String address) {
		logger.info("get number of station covering address : " + address);
		for (Firestation firestation : firestationRepo.getFirestations()) {
			logger.trace("iterate : " + firestation);
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
			logger.trace("iterate : " + person + ", address : " + person.getAddress());
			if (stationAddressList.contains(person.getAddress())) {
				PersonInFirestation personToAdd = new PersonInFirestation();
				personToAdd.setFirstName(person.getFirstName());
				personToAdd.setLastName(person.getLastName());
				personToAdd.setAddress(person.getAddress());
				personToAdd.setPhone(person.getPhone());
				personToAdd.setAge(medicalRecordService.getAge(person.getFirstName(), person.getLastName()));

				logger.info("added " + personToAdd);
				personsInFirestation.add(personToAdd);
			}
		}
		logger.info("persons covered by station " + station + " : " + personsInFirestation);
		return personsInFirestation;
	}

	public PersonsInFirestationWithCount personsInFirestationWithCount(int station) {
		logger.info("get persons covered by firestation " + station + ", counting adults and childs");
		PersonsInFirestationWithCount personsInFirestationWithCount = new PersonsInFirestationWithCount();
		int adults = 0;
		int childrens = 0;

		List<PersonInFirestation> personsInFirestation = getPersonsInFireStation(station);
		for (PersonInFirestation personInFirestation : personsInFirestation) {
			logger.trace("countIterate : " + personInFirestation);
			if (personInFirestation.getAge() < 18) {
				childrens += 1;
			} else {
				adults += 1;
			}
		}
		personsInFirestationWithCount.setPersonsInFireStation(personsInFirestation);
		personsInFirestationWithCount.setAdults(adults);
		personsInFirestationWithCount.setChildrens(childrens);
		logger.info("adults : " + adults + ", childs : " + childrens);
		return personsInFirestationWithCount;
	}

	public Set<String> getPhoneOfPersonsInFirestation(int station) {
		Set<String> phones = new HashSet<String>();
		List<String> stationAddressList = getFirestationAddress(station);
		logger.info("stationAddressList : " + stationAddressList);

		for (Person person : personRepo.getPersons()) {
			logger.trace("iterate : " + person + ", address : " + person.getAddress());
			if (stationAddressList.contains(person.getAddress())) {
				phones.add(person.getPhone());
				logger.info("phone added : " + person.getPhone());
			}
		}
		return phones;
	}

	public boolean addFirestation(Firestation firestation) {
		logger.info("firestation to add : " + firestation);
		List<Firestation> firestations = firestationRepo.getFirestations();
		for (Firestation firestationLoop : firestations) {
			if ((firestationLoop.getAddress().equals(firestation.getAddress())
					&& (firestationLoop.getStation() == firestation.getStation()))) {
				logger.info("firestation : " + firestation + "already exists");
				return false;
			}
		}
		firestations.add(firestation);
		logger.info("resulted firestations : " + firestations);
		ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
		json.setFireStations(firestations);
		jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		return true;
	}

	public boolean modifyFirestation(Firestation firestation) {
		logger.info("firestation to modify : " + firestation);
		boolean modified = false;
		List<Firestation> modifiedFirestations = new ArrayList<Firestation>();
		for (Firestation firestationLoop : firestationRepo.getFirestations()) {
			if (firestationLoop.getAddress().equals(firestation.getAddress())
					&& firestationLoop.getStation() != firestation.getStation()) {
				firestationLoop.setStation(firestation.getStation());
				modified = true;
				logger.info("firestation modified");
			}
			modifiedFirestations.add(firestationLoop);
		}
		if (modified) {
			ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
			json.setFireStations(modifiedFirestations);
			jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		}
		return modified;
	}

	public boolean deleteFirestation(Firestation firestation) {
		logger.info("firestation to delete : " + firestation);
		boolean deleted = false;
		List<Firestation> modifiedFirestations = new ArrayList<Firestation>();
		for (Firestation firestationLoop : firestationRepo.getFirestations()) {
			if (firestationLoop.getAddress().equals(firestation.getAddress())
					&& firestationLoop.getStation() == firestation.getStation()) {
				deleted = true;
				logger.info("firestation deleted");
			} else {
				modifiedFirestations.add(firestationLoop);
			}
		}
		if (deleted) {
			ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
			json.setFireStations(modifiedFirestations);
			jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		}
		return deleted;
	}
}
