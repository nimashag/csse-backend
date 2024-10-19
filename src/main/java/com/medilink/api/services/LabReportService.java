package com.medilink.api.services;

import com.medilink.api.models.LabReport;
import com.medilink.api.repositories.LabReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LabReportService {

    @Autowired
    private LabReportRepository labReportRepository;

    public LabReport addLabReport(LabReport labReport) {
        labReport.setCreatedAt(LocalDateTime.now());
        return labReportRepository.save(labReport);
    }

    public List<LabReport> getReportsForUser(String userId, LocalDateTime dueDate) {
        return labReportRepository.findByUserIdAndDueDate(userId, dueDate);
    }

    public List<LabReport> getReportsDueToday() {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        return labReportRepository.findByDueDate(today);
    }
}
