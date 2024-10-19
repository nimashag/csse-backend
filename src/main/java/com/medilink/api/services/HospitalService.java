package com.medilink.api.services;

import com.medilink.api.models.Hospital;

import java.util.List;

public interface HospitalService {
    Hospital saveHospital(Hospital hospital);
    List<Hospital> getAllHospitals();
    Hospital getHospitalById(String id);
    Hospital updateHospital(String id, Hospital hospital);
    boolean deleteHospital(String id);
}
