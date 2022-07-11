package com.safetynetalerts.controller;

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

import com.safetynetalerts.model.PersonsInFirestationWithCount;
import com.safetynetalerts.model.json.Firestation;
import com.safetynetalerts.service.IFirestationService;

@RestController
public class FireStationController {

	@Autowired
	private IFirestationService firestationService;

	private Logger logger = LoggerFactory.getLogger(getClass());

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
	public ResponseEntity<Void> addFirestation(@RequestBody final Firestation firestation) {
		if (firestationService.addFirestation(firestation)) {
			logger.info("firestation added : " + firestation);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("firestation " + firestation + " already exists, impossible to add it");
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/firestation")
	public ResponseEntity<Void> modifyFirestation(@RequestBody final Firestation firestation) {
		if (firestationService.modifyFirestation(firestation)) {
			logger.info("firestation modified : " + firestation);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("sorry, impossible to modify : " + firestation);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/firestation")
	public ResponseEntity<Void> deleteFirestation(@RequestBody final Firestation firestation) {
		if (firestationService.deleteFirestation(firestation)) {
			logger.info("firestation deleted : " + firestation);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("sorry, impossible to delete : " + firestation);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}