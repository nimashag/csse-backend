package com.medilink.api.enums;

public enum BedType {
    RECEIVED,
    AVAILABLE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
