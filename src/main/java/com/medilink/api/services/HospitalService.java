package com.medilink.api.services;

import com.medilink.api.models.Hospital;

import java.util.List;

/**
 * Service interface for managing Hospital entities.
 */
public interface HospitalService {

    /**
     * Saves a hospital to the repository.
     * @param hospital Hospital entity to save.
     * @return The saved hospital entity.
     */
    Hospital saveHospital(Hospital hospital);

    /**
     * Retrieves all hospitals from the repository.
     * @return List of all hospitals.
     */
    List<Hospital> getAllHospitals();

    /**
     * Retrieves a hospital by its ID.
     * @param id ID of the hospital to retrieve.
     * @return The hospital entity, or null if not found.
     */
    Hospital getHospitalById(String id);

    /**
     * Updates a hospital in the repository.
     * @param id ID of the hospital to update.
     * @param hospital Updated hospital entity.
     * @return The updated hospital entity.
     */
    Hospital updateHospital(String id, Hospital hospital);

    /**
     * Deletes a hospital from the repository by ID.
     * @param id ID of the hospital to delete.
     * @return True if deletion was successful, false otherwise.
     */
    boolean deleteHospital(String id);
}
