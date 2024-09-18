package com.medilink.api.repositories;

import com.medilink.api.models.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
    List<Doctor> findByWorkingHospitals(String hospitalId); // Custom method to find doctors by hospital ID
}
