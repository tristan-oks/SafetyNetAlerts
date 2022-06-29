package com.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.ChildAlert;
import com.safetynetalerts.model.FireWithStationNumber;
import com.safetynetalerts.model.Flood;
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
	public List<Flood> getFlood(@RequestParam(name = "station") final int station) {
		return personService.getFlood(station);
	}
}
