package com.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.service.PersonService;

@RestController
public class GetPersonsInCityController {

	@Autowired
	private PersonService personService;

	@GetMapping("/personsincity")
	public List<Person> getPersonsInCity(@RequestParam(name = "city") final String city) {
		return personService.getPersonsInCity(city);
	}
}