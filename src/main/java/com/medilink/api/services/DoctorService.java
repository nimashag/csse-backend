package com.medilink.api.services;

import com.medilink.api.models.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);
    Doctor updateDoctor(String doctorId, Doctor doctor);
    boolean deleteDoctor(String doctorId);
    Doctor getDoctorById(String doctorId);
    List<Doctor> getDoctorsByHospitalId(String hospitalId);
    List<Doctor> getAllDoctors(); // Add this method declaration
    void addHospitalToDoctor(String doctorId, String hospitalId);
    void removeHospitalFromDoctor(String doctorId, String hospitalId);
    Doctor authenticateDoctor(String email, String password);
}