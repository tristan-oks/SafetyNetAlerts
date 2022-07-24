package com.safetynetalerts.model;

import java.util.List;

public class PersonsInFirestationWithCount {
	private List<PersonInFirestation> personsInFireStation;
	private int adults;
	private int childrens;

	public List<PersonInFirestation> getPersonsInFireStation() {
		return personsInFireStation;
	}

	public void setPersonsInFireStation(List<PersonInFirestation> personsInFireStation) {
		this.personsInFireStation = personsInFireStation;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	public int getChildrens() {
		return childrens;
	}

	public void setChildrens(int childrens) {
		this.childrens = childrens;
	}
}
