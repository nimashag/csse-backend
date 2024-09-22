package com.medilink.api.services;

import com.medilink.api.models.LabTech;

import java.util.List;

/**
 * Service interface for managing LabTech entities.
 */
public interface LabTechService {

    /**
     * Saves a lab technician to the repository.
     * @param labTech LabTech entity to save.
     * @return The saved lab technician entity.
     */
    LabTech saveLabTech(LabTech labTech);

    /**
     * Updates a lab technician in the repository.
     * @param labTechId ID of the lab technician to update.
     * @param labTech Updated lab technician entity.
     * @return The updated lab technician entity.
     */
    LabTech updateLabTech(String labTechId, LabTech labTech);

    /**
     * Deletes a lab technician from the repository.
     * @param labTechId ID of the lab technician to delete.
     * @return True if deletion was successful, false otherwise.
     */
    boolean deleteLabTech(String labTechId);

    /**
     * Retrieves a lab technician by ID.
     * @param labTechId ID of the lab technician to retrieve.
     * @return The lab technician entity.
     */
    LabTech getLabTechById(String labTechId);

    /**
     * Retrieves all lab technicians.
     * @return List of all lab technicians.
     */
    List<LabTech> getAllLabTechs();

    /**
     * Authenticates a lab technician using email and password.
     * @param email LabTech's email.
     * @param password LabTech's password.
     * @return The authenticated lab technician entity, or null if authentication fails.
     */
    LabTech authenticateLabTech(String email, String password);
}
