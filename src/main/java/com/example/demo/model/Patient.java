package com.example.demo.model;

import org.springframework.hateoas.RepresentationModel;

/**
 * @author Taimoor Choudhary
 */

public class Patient extends RepresentationModel<Patient> {

    private int id;
    private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
