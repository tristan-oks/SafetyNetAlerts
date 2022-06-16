package com.safetynetalerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.model.json.ParsedJson;
import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.repository.JsonRepository;

@Service
public class PersonService {
	@Autowired
	private JsonRepository repo;
	
	public List<Person> getPersons() {
		System.out.println("get all persons");
		return repo.parseJSONFile("data.json").getPersons();
	}

	public List<Person> getPersonsInCity(String city) {
		List<Person> personsInCity = new ArrayList<Person>();
		ParsedJson result = repo.parseJSONFile("data.json");
		System.out.println("city : " + city);

		for (Person person : result.getPersons()) {
			System.out.println("iterate : " + person + ", city : " + person.getCity());
			if (person.getCity().equals(city)) {
				System.out.println("added : " + person);
				personsInCity.add(person);
			}
		}
		return personsInCity;
	}
}
