package com.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.ChildAlert;
import com.safetynetalerts.model.FireWithStationNumber;
import com.safetynetalerts.model.Flood;
import com.safetynetalerts.model.PersonInfo;
import com.safetynetalerts.model.json.Person;
import com.safetynetalerts.service.IPersonService;

@RestController
public class PersonController {

	@Autowired
	private IPersonService personService;

	@GetMapping("/communityEmail")
	public List<String> getEmailsOfPersonsInCity(@RequestParam(name = "city") final String city) {
		return personService.getEmailsOfPersonsInCity(city);
	}

	@GetMapping("/childsAtAddress")
	public List<ChildAlert> getChildsAtAddressWithFamily(@RequestParam(name = "address") final String address) {
		return personService.getChildsAtAddressWithFamily(address);
	}

	@GetMapping("/fire")
	public FireWithStationNumber getFireWithStationNumber(@RequestParam(name = "address") final String address) {
		return personService.getFireWithStationNumberAtAddress(address);
	}

	@GetMapping("/flood")
	public List<Flood> getFlood(@RequestParam(name = "stations") final int[] stations) {
		return personService.getFlood(stations);
	}

	@GetMapping("/personInfo")
	public List<PersonInfo> getPersonInfo(@RequestParam(name = "firstName") final String firstName,
			@RequestParam(name = "lastName") final String lastName) {
		return personService.getPersonsInfo(firstName, lastName);
	}

	@PostMapping("/person")
	public String addPerson(final Person person) {
		if (personService.addPerson(person)) {
			return ("person added : " + person);
		} else {
			return ("person " + person + " already exists, impossible to add her/him");
		}
	}

	@PutMapping("/person")
	public String modifyPerson(final Person person) {
		if (personService.modifyPerson(person)) {
			return ("person modified : " + person);
		} else {
			return ("sorry, impossible to modify : " + person);
		}
	}

	@DeleteMapping("/person")
	public String deletePerson(@RequestParam(name = "firstName") final String firstName,
			@RequestParam(name = "lastName") final String lastName) {
		if (personService.deletePerson(firstName, lastName)) {
			return ("person deleted : " + firstName + " " + lastName);
		} else {
			return ("sorry, impossible to delete : " + firstName + " " + lastName);
		}
	}
}
