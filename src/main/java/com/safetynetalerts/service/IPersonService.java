package com.safetynetalerts.service;

import java.util.List;

import com.safetynetalerts.model.ChildAlert;

public interface IPersonService {

	public List<String> getEmailsOfPersonsInCity(String city);

	public List<ChildAlert> getChildsAtAddressWithFamily(String address);
}
