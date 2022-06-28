package com.safetynetalerts.service;

import com.safetynetalerts.model.PersonsInFirestationWithCount;

public interface IFirestationService {

	public PersonsInFirestationWithCount personsInFirestationWithCount(int station);
	
}
