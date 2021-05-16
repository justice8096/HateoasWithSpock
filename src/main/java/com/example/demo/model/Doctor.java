package com.example.demo.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

/**
 * @author Taimoor Choudhary
 */
public class Doctor extends RepresentationModel<Doctor> {

    private int id;
    private String name;
    private String speciality;
    private List<Patient> patientList;
	public List<Patient> getPatientList() {
		// TODO Auto-generated method stub
		return patientList;
	}
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
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}
}
