package com.safetynetalerts.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/communityEmail")
	public Set<String> getEmailsOfPersonsInCity(@RequestParam(name = "city") final String city) {
		return personService.getEmailsOfPersonsInCity(city);
	}

	@GetMapping("/childAlert")
	public List<ChildAlert> getChildsAtAddressWithFamily(@RequestParam(name = "address") final String address) {
		return personService.getChildsAtAddressWithFamily(address);
	}

	@GetMapping("/fire")
	public FireWithStationNumber getFireWithStationNumber(@RequestParam(name = "address") final String address) {
		return personService.getFireWithStationNumberAtAddress(address);
	}

	@GetMapping("/flood/station")
	public List<Flood> getFlood(@RequestParam(name = "stations") final int[] stations) {
		return personService.getFlood(stations);
	}

	@GetMapping("/personInfo")
	public List<PersonInfo> getPersonInfo(@RequestParam(name = "firstName") final String firstName,
			@RequestParam(name = "lastName") final String lastName) {
		return personService.getPersonsInfo(firstName, lastName);
	}

	@PostMapping("/person")
	public ResponseEntity<Void> addPerson(@RequestBody final Person person) {
		if (personService.addPerson(person)) {
			logger.info("person added : " + person);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("person " + person + " already exists, impossible to add her/him");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/person")
	public ResponseEntity<Void> modifyPerson(@RequestBody final Person person) {
		if (personService.modifyPerson(person)) {
			logger.info("person modified : " + person);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("sorry, impossible to modify : " + person);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/person")
	public ResponseEntity<Void> deletePerson(@RequestParam(name = "firstName") final String firstName,
			@RequestParam(name = "lastName") final String lastName) {
		if (personService.deletePerson(firstName, lastName)) {
			logger.info("person deleted : " + firstName + " " + lastName);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("sorry, impossible to delete : " + firstName + " " + lastName);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
