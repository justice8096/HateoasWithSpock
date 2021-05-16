package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Taimoor Choudhary
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private int id;
    private String name;
    private String speciality;
    private List<PatientDto> patientDtoList;
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
	public List<PatientDto> getPatientDtoList() {
		return patientDtoList;
	}
	public List<PatientDto> getPatientList() {
		return patientDtoList;
	}
	public void setPatientDtoList(List<PatientDto> patientDtoList) {
		this.patientDtoList = patientDtoList;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
}
