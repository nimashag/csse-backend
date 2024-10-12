package com.medilink.api.repositories;

import com.medilink.api.models.Bed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BedRepository extends MongoRepository<Bed, String> {
    List<Bed> findByWardId(String wardId);
}
