package com.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.service.PersonService;

@RestController
public class GetPersonsController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/persons")
	public List<Person> getPersons(){
		return personService.getPersons();
	}
}
