package com.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.repository.PersonRepository;

@Service
public class PersonService {
	@Autowired
	private PersonRepository repo;
	

	private Logger logger = LoggerFactory.getLogger(getClass());

	public List<Person> getPersons() {
		logger.info("get all persons");
		return repo.getPersons();
	}

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

	
}