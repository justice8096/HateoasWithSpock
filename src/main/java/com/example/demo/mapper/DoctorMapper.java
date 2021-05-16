package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.example.demo.dto.DoctorDto;
import com.example.demo.dto.PatientDto;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;

public class DoctorMapper {

	public static Doctor toModel(DoctorDto doctorById) {
		Doctor d = new Doctor();
		d.setId(doctorById.getId());
		d.setName(doctorById.getName());
		d.setSpeciality(doctorById.getSpeciality());
		List<PatientDto> pList = doctorById.getPatientDtoList();
		List<Patient> pListOut = new ArrayList<Patient>();
; 		for (PatientDto p: pList) {
			Patient l = new Patient();
			l.setId(p.getId());
			l.setName(p.getName());
			pListOut.add(l);
		}
		d.setPatientList(pListOut);
		return d;
	}

	public static List<Doctor>toModel(List<DoctorDto> doctorDtoList) {
		List<Doctor> d = new ArrayList<Doctor>();
		for(DoctorDto doc: doctorDtoList) {
			d.add(DoctorMapper.toModel(doc));
		}
		return d;
	}

}
