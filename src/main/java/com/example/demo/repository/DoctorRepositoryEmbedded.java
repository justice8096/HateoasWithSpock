package com.example.demo.repository;

import org.springframework.stereotype.Component;

import com.example.demo.dto.DoctorDto;
import com.example.demo.dto.PatientDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Taimoor Choudhary
 */
@Component
public class DoctorRepositoryEmbedded implements DoctorRepository {

    @Override
    public DoctorDto getDoctorById(int id) {

        return createDoctor(id, "Dr. Sanders", "General",null);
    }

    @Override
    public DoctorDto getDoctorByIdWithPatients(int id) {

        List<PatientDto> patientDtoList = new ArrayList<>();

        patientDtoList.add(createPatient(1, "J. Smalling"));
        patientDtoList.add(createPatient(2, "Samantha Williams"));
        DoctorDto doc = createDoctor(id, "Dr. Sanders", "General", patientDtoList);

        return doc;
    }

    @Override
    public List<DoctorDto> getDoctors() {

        List<DoctorDto> doctorDtoList = new ArrayList<>();

        doctorDtoList.add(createDoctor(1, "Dr. Sanders", "General",null));
        doctorDtoList.add(createDoctor(2, "Dr. Goldberg", "General",null));

        return doctorDtoList;
    }

    @Override
    public List<DoctorDto> getDoctorsWithPatients() {

        List<PatientDto> patientDtoList = new ArrayList<>();

        patientDtoList.add(createPatient(1, "J. Smalling"));
        patientDtoList.add(createPatient(2, "Samantha Williams"));
        patientDtoList.add(createPatient(3, "Alfred Tim"));
        patientDtoList.add(createPatient(4, "K. Oliver"));

        List<DoctorDto> doctorDtoList = new ArrayList<>();

        doctorDtoList.add(createDoctor(1, "Dr. Sanders", "General", patientDtoList.subList(0, 2)));
        doctorDtoList.add(createDoctor(2, "Dr. Goldberg", "General", patientDtoList.subList(2, 4)));

        return doctorDtoList;
    }


    private DoctorDto createDoctor(int id, String name, String speciality, List<PatientDto> patientDtos){
    	DoctorDto d = new DoctorDto();
    	d.setId(id);
    	d.setName(name);
    	d.setSpeciality(speciality);
    	d.setPatientDtoList(patientDtos);
        return d;
    }

    private PatientDto createPatient(int id, String name){

    	PatientDto p = new PatientDto();
    	p.setId(id);
    	p.setName(name);
        return p;
    }

}
