package com.medilink.api.Impl;

import com.medilink.api.models.Doctor;
import com.medilink.api.repositories.DoctorRepository;
import com.medilink.api.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        // Encode password before saving
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(String doctorId, Doctor doctor) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(doctorId);
        if (existingDoctor.isPresent()) {
            doctor.setId(doctorId);
            return doctorRepository.save(doctor);
        }
        throw new RuntimeException("Doctor not found");
    }

    @Override
    public boolean deleteDoctor(String id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Doctor getDoctorById(String doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @Override
    public List<Doctor> getDoctorsByHospitalId(String hospitalId) {
        return doctorRepository.findByWorkingHospitals(hospitalId); // Adjust your repository method as needed
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public void addHospitalToDoctor(String doctorId, String hospitalId) {
        Doctor doctor = getDoctorById(doctorId);
        doctor.getWorkingHospitals().add(hospitalId);
        doctorRepository.save(doctor);
    }

    @Override
    public void removeHospitalFromDoctor(String doctorId, String hospitalId) {
        Doctor doctor = getDoctorById(doctorId);
        doctor.getWorkingHospitals().remove(hospitalId);
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor authenticateDoctor(String email, String password) {
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(email);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            if (passwordEncoder.matches(password, doctor.getPassword())) {
                return doctor;
            }
        }
        return null; // Return null if authentication fails
    }

}
