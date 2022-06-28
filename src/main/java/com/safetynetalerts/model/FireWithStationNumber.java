package com.safetynetalerts.model;

import java.util.List;

public class FireWithStationNumber {

	private List<Fire> fire;
	private int stationNumber;
	
	public List<Fire> getFire() {
		return fire;
	}
	public void setFire(List<Fire> fire) {
		this.fire = fire;
	}
	public int getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
	
}
