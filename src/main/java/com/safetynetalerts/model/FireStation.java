package com.safetynetalerts.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Component
public class FireStation {
	@JsonProperty("address")
	private String address;
	@JsonProperty("station")
	private int station;
}
