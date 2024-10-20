package com.medilink.api.repositories;

import com.medilink.api.models.LabTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing LabTest data in MongoDB.
 */
@Repository
public interface LabTestRepository extends MongoRepository<LabTest, String> {
    List<LabTest> findByHospitalId(String hospitalId);
}
