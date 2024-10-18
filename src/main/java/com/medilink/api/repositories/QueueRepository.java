package com.medilink.api.repositories;

import com.medilink.api.models.Queue;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QueueRepository extends MongoRepository<Queue, String> {
}
