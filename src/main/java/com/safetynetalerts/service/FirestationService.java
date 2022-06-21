package com.safetynetalerts.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynetalerts.model.json.Firestation;
import com.safetynetalerts.repository.FirestationRepository;

@Service
public class FirestationService {
	@Autowired
	private FirestationRepository repo;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public boolean belongToFirestation(int stationNumber) {

		for (Firestation firestation : repo.getFirestations()) {
			logger.info("iterate : " + firestation + ", station : " + firestation.getStation());
			if (firestation.getStation() == stationNumber) {
				logger.info("found : " + firestation);
				return true;
			}
		}
		return false;
	}
	
	public boolean belongToFirestation(String address) {

		for (Firestation firestation : repo.getFirestations()) {
			logger.info("iterate : " + firestation + ", stationAddress : " + firestation.getAddress());
			if (firestation.getAddress().equals(address)) {
				logger.info("found : " + firestation);
				return true;
			}
		}
		return false;
	}

	public List<String> getFirestation(int stationNumber) {
		// TODO Auto-generated method stub
		return null;
	}
}
