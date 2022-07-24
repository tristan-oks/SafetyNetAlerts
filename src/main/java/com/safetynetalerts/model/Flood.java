package com.safetynetalerts.model;

import java.util.List;

public class Flood {
	private String address;
	private List<PersonWithMedicalRecord> personAtAddressWithMedicalRecords;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<PersonWithMedicalRecord> getPersonAtAddressWithMedicalRecords() {
		return personAtAddressWithMedicalRecords;
	}

	public void setPersonAtAddressWithMedicalRecords(
			List<PersonWithMedicalRecord> personAtAddressWithMedicalRecords) {
		this.personAtAddressWithMedicalRecords = personAtAddressWithMedicalRecords;
	}

	@Override
	public String toString() {
		return "Flood [address=" + address + ", personAtAddressWithMedicalRecords=" + personAtAddressWithMedicalRecords
				+ "]";
	}

}
