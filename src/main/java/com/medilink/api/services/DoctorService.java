package com.medilink.api.services;

import com.medilink.api.models.Doctor;

import java.util.List;

/**
 * Service interface for managing Doctor entities.
 */
public interface DoctorService {

    /**
     * Saves a doctor to the repository.
     * @param doctor Doctor entity to save.
     * @return The saved doctor entity.
     */
    Doctor saveDoctor(Doctor doctor);

    /**
     * Updates a doctor in the repository.
     * @param doctorId ID of the doctor to update.
     * @param doctor Updated doctor entity.
     * @return The updated doctor entity.
     */
    Doctor updateDoctor(String doctorId, Doctor doctor);

    /**
     * Deletes a doctor from the repository.
     * @param doctorId ID of the doctor to delete.
     * @return True if deletion was successful, false otherwise.
     */
    boolean deleteDoctor(String doctorId);

    /**
     * Retrieves a doctor by ID.
     * @param doctorId ID of the doctor to retrieve.
     * @return The doctor entity.
     */
    Doctor getDoctorById(String doctorId);

    /**
     * Retrieves doctors associated with a specific hospital ID.
     * @param hospitalId ID of the hospital.
     * @return List of doctors associated with the specified hospital.
     */
    List<Doctor> getDoctorsByHospitalId(String hospitalId);

    /**
     * Retrieves all doctors.
     * @return List of all doctors.
     */
    List<Doctor> getAllDoctors(); // Add this method declaration

    /**
     * Adds a hospital to a doctor's list of hospitals.
     * @param doctorId ID of the doctor.
     * @param hospitalId ID of the hospital to add.
     */
    void addHospitalToDoctor(String doctorId, String hospitalId);

    /**
     * Removes a hospital from a doctor's list of hospitals.
     * @param doctorId ID of the doctor.
     * @param hospitalId ID of the hospital to remove.
     */
    void removeHospitalFromDoctor(String doctorId, String hospitalId);

    /**
     * Authenticates a doctor using email and password.
     * @param email Doctor's email.
     * @param password Doctor's password.
     * @return The authenticated doctor entity, or null if authentication fails.
     */
    Doctor authenticateDoctor(String email, String password);
}