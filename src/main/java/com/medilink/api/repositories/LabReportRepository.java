package com.medilink.api.repositories;

import com.medilink.api.models.LabReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LabReportRepository extends MongoRepository<LabReport, String> {
    List<LabReport> findByUserIdAndDueDate(String userId, LocalDateTime dueDate);
    List<LabReport> findByDueDate(LocalDateTime dueDate); // For retrieving today's due reports
}
