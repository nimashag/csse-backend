package com.medilink.api.repositories;

import com.medilink.api.models.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Hospital data in MongoDB.
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    // This interface inherits all CRUD operations from MongoRepository. Additional methods can be added as needed.
}
