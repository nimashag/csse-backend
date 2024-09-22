package com.medilink.api.Impl;

import com.medilink.api.models.LabTech;
import com.medilink.api.repositories.LabTechRepository;
import com.medilink.api.services.LabTechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the LabTechService interface for managing lab technician data.
 */
@Service
public class LabTechServiceImpl implements LabTechService {

    private final LabTechRepository labTechRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for LabTechServiceImpl.
     * @param labTechRepository Repository for lab technician data.
     * @param passwordEncoder Password encoder.
     */
    @Autowired
    public LabTechServiceImpl(LabTechRepository labTechRepository, PasswordEncoder passwordEncoder) {
        this.labTechRepository = labTechRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a lab technician to the repository.
     * @param labTech LabTech entity to save.
     * @return The saved lab technician entity.
     */
    @Override
    public LabTech saveLabTech(LabTech labTech) {
        // Encode password before saving
        labTech.setPassword(passwordEncoder.encode(labTech.getPassword()));
        return labTechRepository.save(labTech);
    }

    /**
     * Updates a lab technician in the repository.
     * @param labTechId ID of the lab technician to update.
     * @param labTech Updated lab technician entity.
     * @return The updated lab technician entity, or throw exception if not found.
     */
    @Override
    public LabTech updateLabTech(String labTechId, LabTech labTech) {
        Optional<LabTech> existingLabTech = labTechRepository.findById(labTechId);
        if (existingLabTech.isPresent()) {
            if (labTech.getPassword() == null) {
                labTech.setPassword(existingLabTech.get().getPassword());
            } else {
                labTech.setPassword(passwordEncoder.encode(labTech.getPassword()));
            }
            labTech.setId(labTechId);
            return labTechRepository.save(labTech);
        }
        throw new RuntimeException("Lab Technician not found");
    }

    /**
     * Deletes a lab technician from the repository by ID.
     * @param labTechId ID of the lab technician to delete.
     * @return True if deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteLabTech(String labTechId) {
        if (labTechRepository.existsById(labTechId)) {
            labTechRepository.deleteById(labTechId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves a lab technician by ID from the repository.
     * @param labTechId ID of the lab technician to retrieve.
     * @return The lab technician entity, or throw exception if not found.
     */
    @Override
    public LabTech getLabTechById(String labTechId) {
        return labTechRepository.findById(labTechId)
                .orElseThrow(() -> new RuntimeException("Lab Technician not found"));
    }

    /**
     * Retrieves all lab technicians from the repository.
     * @return List of all lab technicians.
     */
    @Override
    public List<LabTech> getAllLabTechs() {
        return labTechRepository.findAll();
    }

    /**
     * Authenticates a lab technician by email and password.
     * @param email LabTech's email.
     * @param password LabTech's password.
     * @return The authenticated lab technician entity, or null if authentication fails.
     */
    @Override
    public LabTech authenticateLabTech(String email, String password) {
        Optional<LabTech> labTechOptional = labTechRepository.findByEmail(email);
        if (labTechOptional.isPresent()) {
            LabTech labTech = labTechOptional.get();
            if (passwordEncoder.matches(password, labTech.getPassword())) {
                return labTech;
            }
        }
        return null; // Return null if authentication fails
    }
}
