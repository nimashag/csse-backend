package com.medilink.api.repositories;

import com.medilink.api.models.Ward;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WardRepository extends MongoRepository<Ward, String> {
    List<Ward> findByHospital_HospitalId(String hospital);
}
