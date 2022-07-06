package com.safetynetalerts.model;

import java.util.List;

import com.safetynetalerts.model.json.Person;

public class ChildAlert {
	private String firstName;
	private String lastName;
	private int age;
	private List<Person> homeMembers;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Person> getHomeMembers() {
		return homeMembers;
	}

	public void setHomeMembers(List<Person> homeMembers) {
		this.homeMembers = homeMembers;
	}

	@Override
	public String toString() {
		return "ChildAlert [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", homeMembers="
				+ homeMembers + "]";
	}

}
