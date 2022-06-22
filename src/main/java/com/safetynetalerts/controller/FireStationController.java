package com.safetynetalerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.PersonInFirestation;
import com.safetynetalerts.model.PersonsInFirestationWithCount;
import com.safetynetalerts.service.FirestationService;

@RestController
public class FireStationController {

	@Autowired
	private FirestationService firestationService;
	
	
	@GetMapping("/firestationByNumber")
	public boolean getPersonsCoveredByStationNumber(@RequestParam(name = "stationNumber") final int stationNumber) {
		return firestationService.belongToFirestation(stationNumber);
	}

	@GetMapping("/firestationByAddress")
	public boolean getPersonsCoveredByStationAddress(
			@RequestParam(name = "stationAddress") final String stationAddress) {
		return firestationService.belongToFirestation(stationAddress);
	}
	
	@GetMapping("/firestationAddressByStation")
	public String getFirestationAddressByStation(
			@RequestParam(name = "stationNumber") final int stationNumber) {
		return firestationService.getFirestationAddress(stationNumber);
	}

	@GetMapping("/firestation")
	public PersonsInFirestationWithCount getPersonsInFirestationWithCount(
			@RequestParam(name = "stationNumber") final int station) {
		List<PersonInFirestation> personsInFirestation = firestationService.getPersonsInFireStation(station);
		return firestationService.personsInFirestationWithCount(personsInFirestation);
	}
}