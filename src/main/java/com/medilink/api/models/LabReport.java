package com.medilink.api.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "labReports")
public class LabReport {
    @Id
    private String id;
    private String userId; // This will link the report to a user
    private String testType;
    private LocalDateTime dueDate;
    private String reportLink;
    private LocalDateTime createdAt;

    public LabReport() {
    }

    // Constructors, Getters, and Setters
    public LabReport(String userId, String testType, LocalDateTime dueDate, String reportLink, LocalDateTime createdAt) {
        this.userId = userId;
        this.testType = testType;
        this.dueDate = dueDate;
        this.reportLink = reportLink;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getReportLink() {
        return reportLink;
    }

    public void setReportLink(String reportLink) {
        this.reportLink = reportLink;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
