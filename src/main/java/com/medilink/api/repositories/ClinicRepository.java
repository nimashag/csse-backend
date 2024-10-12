package com.medilink.api.repositories;

import com.medilink.api.models.Clinic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends MongoRepository<Clinic, String> {
}
