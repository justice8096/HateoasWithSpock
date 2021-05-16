package com.example.demo.service;

import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Taimoor Choudhary
 */
@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient getPatient(int id){

        return PatientMapper.toModel(patientRepository.getPatientById(id));
    }
}
