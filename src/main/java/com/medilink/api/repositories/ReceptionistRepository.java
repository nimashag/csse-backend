package com.medilink.api.repositories;

import com.medilink.api.models.Receptionist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReceptionistRepository extends MongoRepository<Receptionist, String> {
    Optional<Receptionist> findByEmail(String email);
}
