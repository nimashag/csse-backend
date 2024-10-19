package com.medilink.api.Impl;

import com.medilink.api.models.Hospital;
import com.medilink.api.repositories.HospitalRepository;
import com.medilink.api.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the HospitalService interface for managing hospital data.
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    /**
     * Saves a hospital to the repository.
     * @param hospital Hospital entity to save.
     * @return The saved hospital entity.
     */
    @Override
    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    /**
     * Retrieves all hospitals from the repository.
     * @return List of all hospitals.
     */
    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    /**
     * Retrieves a hospital by ID from the repository.
     * @param id ID of the hospital to retrieve.
     * @return The hospital entity, or null if not found.
     */
    @Override
    public Hospital getHospitalById(String id) {
        return hospitalRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing hospital by its ID.
     * @param id       ID of the hospital to update.
     * @param hospital Updated hospital entity.
     * @return The updated hospital entity, or null if not found.
     */
    @Override
    public Hospital updateHospital(String id, Hospital hospital) {
        Optional<Hospital> existingHospital = hospitalRepository.findById(id);
        if (existingHospital.isPresent()) {
            hospital.setHospitalId(id);  // Keep the existing ID
            return hospitalRepository.save(hospital);  // Update and save
        } else {
            return null;
        }
    }

    /**
     * Deletes a hospital from the repository by ID.
     * @param id ID of the hospital to delete.
     * @return True if deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteHospital(String id) {
        if (hospitalRepository.existsById(id)) {
            hospitalRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
