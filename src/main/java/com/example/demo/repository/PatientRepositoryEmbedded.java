package com.example.demo.repository;

import org.springframework.stereotype.Component;

import com.example.demo.dto.PatientDto;

/**
 * @author Taimoor Choudhary
 */
@Component
public class PatientRepositoryEmbedded implements PatientRepository {

    @Override
    public PatientDto getPatientById(int id) {
        return createPatient(id, "Samuel Bradford");
    }

    private PatientDto createPatient(int id, String name){
    	PatientDto p = new PatientDto();
    	p.setId(id);
    	p.setName(name);
        return p;
    }
}
