package com.example.demo.repository;

import com.example.demo.dto.PatientDto;

/**
 * @author Taimoor Choudhary
 */
public interface PatientRepository {

    PatientDto getPatientById(int id);
}
