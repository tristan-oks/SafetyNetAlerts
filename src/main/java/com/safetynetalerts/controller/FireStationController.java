package com.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.PersonsInFirestationWithCount;
import com.safetynetalerts.service.IFirestationService;

@RestController
public class FireStationController {

	@Autowired
	private IFirestationService firestationService;

	@GetMapping("/firestation")
	public PersonsInFirestationWithCount getPersonsInFirestationWithCount(
			@RequestParam(name = "stationNumber") final int station) {
		return firestationService.personsInFirestationWithCount(station);
	}

	@GetMapping("/phoneAlert")
	public List<String> getPhonesOfPersonsInFirestation(@RequestParam(name = "firestation") final int station) {
		return firestationService.getPhoneOfPersonsInFirestation(station);
	}

}