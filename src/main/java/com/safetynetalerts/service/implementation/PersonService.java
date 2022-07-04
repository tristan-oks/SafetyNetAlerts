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
import com.safetynetalerts.model.Child;
import com.safetynetalerts.model.ChildAlert;
import com.safetynetalerts.model.PersonWithMedicalRecord;
import com.safetynetalerts.model.FireWithStationNumber;
import com.safetynetalerts.model.Flood;
import com.safetynetalerts.model.PersonInfo;
import com.safetynetalerts.model.json.ParsedJson;
import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.repository.JsonRepository;
import com.safetynetalerts.repository.PersonRepository;
import com.safetynetalerts.service.IFirestationService;
import com.safetynetalerts.service.IMedicalRecordService;
import com.safetynetalerts.service.IPersonService;

@Service
public class PersonService implements IPersonService {
	@Autowired
	private JsonRepository jsonRepo;
	@Autowired
	private PersonRepository repo;
	@Autowired
	private IMedicalRecordService medicalRecordService;
	@Autowired
	private IFirestationService firestationService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public Set<String> getEmailsOfPersonsInCity(String city) {
		Set<String> emails = new HashSet<String>();
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

	private List<PersonWithMedicalRecord> getFireAtAddress(String address) {
		List<PersonWithMedicalRecord> fireAtAddress = new ArrayList<PersonWithMedicalRecord>();
		List<Person> personsAtAddress = getPersonsAtAddress(address);
		for (Person person : personsAtAddress) {
			PersonWithMedicalRecord fireToAdd = new PersonWithMedicalRecord();
			fireToAdd.setFirstName(person.getFirstName());
			fireToAdd.setLastName(person.getLastName());
			fireToAdd.setPhone(person.getPhone());
			fireToAdd.setAge(medicalRecordService.getAge(person.getFirstName(), person.getLastName()));
			fireToAdd.setMedications(medicalRecordService.getMedications(person.getFirstName(), person.getLastName()));
			fireToAdd.setAllergies(medicalRecordService.getAllergies(person.getFirstName(), person.getLastName()));
			fireAtAddress.add(fireToAdd);
		}
		return fireAtAddress;
	}

	public FireWithStationNumber getFireWithStationNumberAtAddress(String address) {
		FireWithStationNumber fireWithStationNumber = new FireWithStationNumber();
		fireWithStationNumber.setFire(getFireAtAddress(address));
		fireWithStationNumber.setStationNumber(firestationService.getFirestationNumber(address));
		return fireWithStationNumber;
	}

	public List<PersonWithMedicalRecord> getPersonsAtAddressWithMedicalRecords(String address) {
		logger.info("get all persons with their medical records at address : " + address);

		List<PersonWithMedicalRecord> personsAtAddressWithMedicalRecords = new ArrayList<PersonWithMedicalRecord>();

		for (Person person : repo.getPersons()) {
			logger.info("iterate : " + person + ", address : " + person.getAddress());
			if (person.getAddress().equals(address)) {
				PersonWithMedicalRecord personToAdd = new PersonWithMedicalRecord();
				personToAdd.setFirstName(person.getFirstName());
				personToAdd.setLastName(person.getLastName());
				personToAdd.setPhone(person.getPhone());
				personToAdd.setAge(medicalRecordService.getAge(person.getFirstName(), person.getLastName()));
				personToAdd.setMedications(
						medicalRecordService.getMedications(person.getFirstName(), person.getLastName()));
				personToAdd
						.setAllergies(medicalRecordService.getAllergies(person.getFirstName(), person.getLastName()));

				logger.info("person at address with medical records added : " + personToAdd.getFirstName() + " "
						+ personToAdd.getLastName() + " " + personToAdd.getPhone() + " " + personToAdd.getAge() + " "
						+ personToAdd.getMedications() + " " + personToAdd.getAllergies());
				personsAtAddressWithMedicalRecords.add(personToAdd);
			}
		}
		return personsAtAddressWithMedicalRecords;
	}

	public List<Flood> getFlood(int[] stations) {
		List<Flood> flood = new ArrayList<Flood>();
		for (int station : stations) {
			logger.info("processing station : " + station);
			List<String> stationAddressList = firestationService.getFirestationAddress(station);

			logger.info("stationAddressList : " + stationAddressList);
			for (String address : stationAddressList) {
				Flood floodToAdd = new Flood();
				floodToAdd.setAddress(address);
				floodToAdd.setPersonAtAddressWithMedicalRecords(getPersonsAtAddressWithMedicalRecords(address));

				logger.info("Flood added : " + floodToAdd);
				flood.add(floodToAdd);
			}
		}
		return flood;
	}

	public List<PersonInfo> getPersonsInfo(String firstName, String lastName) {
		List<PersonInfo> personsInfo = new ArrayList<PersonInfo>();
		for (Person person : repo.getPersons()) {
			logger.info("iterate : " + person);
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				PersonInfo personInfoToAdd = new PersonInfo();
				personInfoToAdd.setFirstName(firstName);
				personInfoToAdd.setLastName(lastName);
				personInfoToAdd.setAddress(person.getAddress());
				personInfoToAdd.setAge(medicalRecordService.getAge(firstName, lastName));
				personInfoToAdd.setEmail(person.getEmail());
				personInfoToAdd.setMedications(medicalRecordService.getMedications(firstName, lastName));
				personInfoToAdd.setAllergies(medicalRecordService.getAllergies(firstName, lastName));
				logger.info("added : " + personInfoToAdd);
				personsInfo.add(personInfoToAdd);
			}
		}
		return personsInfo;
	}

	public boolean addPerson(Person person) {
		logger.info("person to add : " + person);
		List<Person> persons = repo.getPersons();
		// if (persons.contains(person)) {
		for (Person personLoop : persons) {
			if ((personLoop.getFirstName().equals(person.getFirstName()))
					&& (personLoop.getLastName().equals(person.getLastName()))
					&& (personLoop.getAddress().equals(person.getAddress()))
					&& (personLoop.getCity().equals(person.getCity())) && (personLoop.getZip() == person.getZip())
					&& (personLoop.getPhone().equals(person.getPhone()))
					&& (personLoop.getEmail().equals(person.getEmail()))) {
				logger.info("person : " + person + "already exists");
				return false;
			}
		}
		persons.add(person);
		logger.info("resulted persons : " + persons);
		ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
		json.setPersons(persons);
		jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		return true;
	}

	public boolean modifyPerson(Person person) {
		logger.info("person to modify : " + person);
		boolean modified = false;
		List<Person> modifiedPersons = new ArrayList<Person>();
		// if (persons.contains(person)) {
		for (Person personLoop : repo.getPersons()) {
			if ((personLoop.getFirstName().equals(person.getFirstName()))
					&& (personLoop.getLastName().equals(person.getLastName()))
					&& (!(personLoop.getAddress().equals(person.getAddress()))
							|| !(personLoop.getCity().equals(person.getCity()))
							|| (personLoop.getZip() != person.getZip())
							|| !(personLoop.getPhone().equals(person.getPhone()))
							|| !(personLoop.getEmail().equals(person.getEmail())))) {
				personLoop = person;
				modified = true;
				logger.info("person : " + person + "modified");
			}
			modifiedPersons.add(personLoop);
		}
		if (modified) {
			logger.info("resulted persons : " + modifiedPersons);
			ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
			json.setPersons(modifiedPersons);
			jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		}
		return modified;
	}

	public boolean deletePerson(String firstName, String lastName) {
		logger.info("person to delete : " + firstName + lastName);
		boolean deleted = false;
		List<Person> modifiedPersons = new ArrayList<Person>();
		for (Person personLoop : repo.getPersons()) {
			if (personLoop.getFirstName().equals(firstName) && personLoop.getLastName().equals(lastName)) {
				deleted = true;
				logger.info("person : " + personLoop + "deleted");
			} else {
				modifiedPersons.add(personLoop);
			}
		}
		if (deleted) {
			logger.info("resulted persons : " + modifiedPersons);
			ParsedJson json = jsonRepo.parseJSONFile(Constants.JSON_FILENAME);
			json.setPersons(modifiedPersons);
			jsonRepo.serializeJsonToFile(json, Constants.RESULT_FILENAME);
		}
		return deleted;
	}
}
