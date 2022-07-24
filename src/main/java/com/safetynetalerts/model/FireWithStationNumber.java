package com.safetynetalerts.model;

import java.util.List;

public class FireWithStationNumber {

	private List<PersonWithMedicalRecord> fire;
	private int stationNumber;
	
	public List<PersonWithMedicalRecord> getFire() {
		return fire;
	}
	public void setFire(List<PersonWithMedicalRecord> fire) {
		this.fire = fire;
	}
	public int getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
	
}
