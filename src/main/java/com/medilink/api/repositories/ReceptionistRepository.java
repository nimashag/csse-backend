package com.medilink.api.repositories;

import com.medilink.api.models.Receptionist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReceptionistRepository extends MongoRepository<Receptionist, String> {
}
