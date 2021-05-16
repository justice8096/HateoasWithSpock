package com.example.demo.service;

import com.example.demo.dto.DoctorDto;
import com.example.demo.mapper.DoctorMapper;
import com.example.demo.model.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Taimoor Choudhary
 */
@Service
public class DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    public Doctor getDoctor(int id){

        return DoctorMapper.toModel(doctorRepository.getDoctorById(id));
    }

    public Doctor getDoctorWithPatients(int id){

        return DoctorMapper.toModel(doctorRepository.getDoctorByIdWithPatients(id));
    }

    public List<Doctor> getDoctors(){

        List<DoctorDto> doctorDtoList = doctorRepository.getDoctors();

        return DoctorMapper.toModel(doctorDtoList);
    }

    public List<Doctor> getDoctorsWithPatients(){

        List<DoctorDto> doctorDtoList = doctorRepository.getDoctorsWithPatients();

        return DoctorMapper.toModel(doctorDtoList);
    }
}
