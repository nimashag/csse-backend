package com.medilink.api.repositories;

import com.medilink.api.models.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Patient findByEmail(String email);
}
