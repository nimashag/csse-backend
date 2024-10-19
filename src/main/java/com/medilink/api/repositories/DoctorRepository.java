package com.medilink.api.repositories;

import com.medilink.api.models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing Doctor data in MongoDB.
 */
@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
    // This interface inherits all CRUD operations from MongoRepository. Additional methods can be added as needed.

    /**
     * Custom method to find doctors by hospital ID.
     * @param hospitalId ID of the hospital.
     * @return List of doctors associated with the specified hospital.
     */
    List<Doctor> findByWorkingHospitals(String hospitalId);

    /**
     * Custom method to find a doctor by email.
     * @param email Email of the doctor.
     * @return Optional containing the doctor if found, or empty if not.
     */
    Optional<Doctor> findByEmail(String email);
}
