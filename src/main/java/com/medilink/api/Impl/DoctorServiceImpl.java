package com.medilink.api.Impl;

import com.medilink.api.models.Doctor;
import com.medilink.api.repositories.DoctorRepository;
import com.medilink.api.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the DoctorService interface for managing doctor data.
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for DoctorServiceImpl.
     * @param doctorRepository Repository for doctor data.
     * @param passwordEncoder Password encoder.
     */
    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a doctor to the repository.
     * @param doctor Doctor entity to save.
     * @return The saved doctor entity.
     */
    @Override
    public Doctor saveDoctor(Doctor doctor) {
        // Encode password before saving
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    /**
     * Updates a doctor in the repository.
     * @param doctorId ID of the doctor to update.
     * @param doctor Updated doctor entity.
     * @return The updated doctor entity, or throw exception if not found.
     */
    @Override
    public Doctor updateDoctor(String doctorId, Doctor doctor) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(doctorId);
        if (existingDoctor.isPresent()) {
            if (doctor.getPassword() == null) {
                doctor.setPassword(existingDoctor.get().getPassword());
            } else {
                doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            }
            doctor.setId(doctorId);
            return doctorRepository.save(doctor);
        }
        throw new RuntimeException("Doctor not found");
    }

    /**
     * Deletes a doctor from the repository by ID.
     * @param id ID of the doctor to delete.
     * @return True if deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteDoctor(String id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves a doctor by ID from the repository.
     * @param doctorId ID of the doctor to retrieve.
     * @return The doctor entity, or throw exception if not found.
     */
    @Override
    public Doctor getDoctorById(String doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    /**
     * Retrieves doctors associated with a specific hospital ID.
     * @param hospitalId ID of the hospital.
     * @return List of doctors associated with the specified hospital.
     */
    @Override
    public List<Doctor> getDoctorsByHospitalId(String hospitalId) {
        return doctorRepository.findByWorkingHospitals(hospitalId); // Adjust your repository method as needed
    }

    /**
     * Retrieves all doctors from the repository.
     * @return List of all doctors.
     */
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Adds a hospital to a doctor.
     * @param doctorId ID of the doctor to update.
     * @param hospitalId ID of the hospital to assign.
     */
    @Override
    public void addHospitalToDoctor(String doctorId, String hospitalId) {
        Doctor doctor = getDoctorById(doctorId);
        doctor.getWorkingHospitals().add(hospitalId);
        doctorRepository.save(doctor);
    }

    /**
     * Removes a hospital from a doctor.
     * @param doctorId ID of the doctor to update.
     * @param hospitalId ID of the hospital to remove.
     */
    @Override
    public void removeHospitalFromDoctor(String doctorId, String hospitalId) {
        Doctor doctor = getDoctorById(doctorId);
        doctor.getWorkingHospitals().remove(hospitalId);
        doctorRepository.save(doctor);
    }

    /**
     * Authenticates a doctor by email and password.
     * @param email Doctor's email.
     * @param password Doctor's password.
     * @return The authenticated doctor entity, or null if authentication fails.
     */
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
