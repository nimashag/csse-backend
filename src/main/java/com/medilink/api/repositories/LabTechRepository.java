package com.medilink.api.repositories;

import com.medilink.api.models.LabTech;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing LabTech data in MongoDB.
 */
@Repository
public interface LabTechRepository extends MongoRepository<LabTech, String> {
    // This interface inherits all CRUD operations from MongoRepository. Additional methods can be added as needed.

    /**
     * Custom method to find a doctor by email.
     * @param email Email of the doctor.
     * @return Optional containing the doctor if found, or empty if not.
     */
    Optional<LabTech> findByEmail(String email);
}
