package com.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.model.Child;
import com.safetynetalerts.model.ChildAlert;
import com.safetynetalerts.model.Fire;
import com.safetynetalerts.model.FireWithStationNumber;
import com.safetynetalerts.model.Flood;
import com.safetynetalerts.model.PersonAtAddressWithMedicalRecords;
import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.repository.PersonRepository;

@Service
public class PersonService implements IPersonService {
	@Autowired
	private PersonRepository repo;
	@Autowired
	private IMedicalRecordService medicalRecordService;
	@Autowired
	private IFirestationService firestationService;

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

	private List<Fire> getFireAtAddress(String address) {
		List<Fire> fireAtAddress = new ArrayList<Fire>();
		List<Person> personsAtAddress = getPersonsAtAddress(address);
		for (Person person : personsAtAddress) {
			Fire fireToAdd = new Fire();
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

	public List<PersonAtAddressWithMedicalRecords> getPersonsAtAddressWithMedicalRecords(String address) {
		logger.info("get all persons with their medical records at address : " + address);

		List<PersonAtAddressWithMedicalRecords> personsAtAddressWithMedicalRecords = new ArrayList<PersonAtAddressWithMedicalRecords>();

		for (Person person : repo.getPersons()) {
			logger.info("iterate : " + person + ", address : " + person.getAddress());
			if (person.getAddress().equals(address)) {
				PersonAtAddressWithMedicalRecords personToAdd = new PersonAtAddressWithMedicalRecords();
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
}