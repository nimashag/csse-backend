package com.medilink.api.repositories;

import com.medilink.api.models.WardHead;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WardHeadRepository extends MongoRepository<WardHead, String> {
}
