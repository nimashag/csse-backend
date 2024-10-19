package com.medilink.api.controllers;

import com.medilink.api.models.LabReport;
import com.medilink.api.services.LabReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lab-reports")
public class LabReportController {

    @Autowired
    private LabReportService labReportService;

    @PostMapping("/add")
    public ResponseEntity<LabReport> addLabReport(@RequestBody LabReport labReport) {
        LabReport savedReport = labReportService.addLabReport(labReport);
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LabReport>> getReportsForUser(@PathVariable String userId, @RequestParam("dueDate") LocalDateTime dueDate) {
        List<LabReport> reports = labReportService.getReportsForUser(userId, dueDate);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/due-today")
    public ResponseEntity<List<LabReport>> getReportsDueToday() {
        List<LabReport> reports = labReportService.getReportsDueToday();
        return ResponseEntity.ok(reports);
    }
}
