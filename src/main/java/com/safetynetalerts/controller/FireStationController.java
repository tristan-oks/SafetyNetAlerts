package com.safetynetalerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.service.FirestationService;

@RestController
public class FireStationController {

	@Autowired
	private FirestationService firestationService;

	@GetMapping("/firestation")
	public boolean getPersonsCoveredByStationNumber(@RequestParam(name = "stationNumber") final int stationNumber) {
		return firestationService.belongToFirestation(stationNumber);
	}

	@GetMapping("/firestation")
	public boolean getPersonsCoveredByStationAddress(@RequestParam(name = "stationAddress") final String stationAddress) {
		return firestationService.belongToFirestation(stationAddress);
	}
}