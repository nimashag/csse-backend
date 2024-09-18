package com.medilink.api.enums;

public enum UserType {
    SUPER_ADMIN,
    DOCTOR,
    PATIENT,
    LAB_TECH,
    RECEPTIONIST,
    WARD_HEAD,
    HEALTHCARE_MANAGER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
