package com.example.demo.mapper;

import com.example.demo.dto.PatientDto;
import com.example.demo.model.Patient;

public class PatientMapper {

	public static Patient toModel(PatientDto patientById) {
		Patient p = new Patient();
		p.setId(patientById.getId());
		p.setName(patientById.getName());
		return p;
	}

}
