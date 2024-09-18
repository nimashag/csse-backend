package com.medilink.api.enums;

public enum HospitalType {
    PRIVATE_HOSPITAL,
    PUBLIC_HOSPITAL;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
