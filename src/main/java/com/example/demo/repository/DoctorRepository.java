package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.DoctorDto;

/**
 * @author Taimoor Choudhary
 */
public interface DoctorRepository {

    DoctorDto getDoctorById(int id);
    DoctorDto getDoctorByIdWithPatients(int id);
    List<DoctorDto> getDoctors();
    List<DoctorDto> getDoctorsWithPatients();
}
