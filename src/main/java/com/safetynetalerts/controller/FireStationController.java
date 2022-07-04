package com.safetynetalerts.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynetalerts.model.PersonsInFirestationWithCount;
import com.safetynetalerts.model.json.Firestation;
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
	public Set<String> getPhonesOfPersonsInFirestation(@RequestParam(name = "firestation") final int station) {
		return firestationService.getPhoneOfPersonsInFirestation(station);
	}

	@PostMapping("/firestation")
	public String addFirestation(final Firestation firestation) {
		if (firestationService.addFirestation(firestation)) {
			return ("firestation added : " + firestation);
		} else {
			return ("firestation " + firestation + " already exists, impossible to add it");
		}
	}

	@PutMapping("/firestation")
	public String modifyFirestation(final Firestation firestation) {
		if (firestationService.modifyFirestation(firestation)) {
			return ("firestation modified : " + firestation);
		} else {
			return ("sorry, impossible to modify : " + firestation);
		}
	}

	@DeleteMapping("/firestation")
	public String deleteFirestation(final Firestation firestation) {
		if (firestationService.deleteFirestation(firestation)) {
			return ("firestation deleted : " + firestation);
		} else {
			return ("sorry, impossible to delete : " + firestation);
		}
	}
}