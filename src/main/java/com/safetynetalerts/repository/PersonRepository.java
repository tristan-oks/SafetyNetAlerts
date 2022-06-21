package com.safetynetalerts.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynetalerts.model.json.Person;

@Component
public class PersonRepository {
	@Autowired
	private JsonRepository repo;

	public List<Person> getPersons(){
		return repo.parseJSONFile("data.json").getPersons();
	}
	
}
