package com.medilink.api.services;

import com.medilink.api.models.Hospital;
import com.medilink.api.repositories.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital getHospitalById(String id) {
        return hospitalRepository.findById(id).orElse(null);
    }

    public Hospital updateHospital(String id, Hospital hospital) {
        Optional<Hospital> existingHospital = hospitalRepository.findById(id);
        if (existingHospital.isPresent()) {
            hospital.setHospitalId(id);  // Keep the existing ID
            return hospitalRepository.save(hospital);  // Update and save
        } else {
            return null;
        }
    }

    public boolean deleteHospital(String id) {
        if (hospitalRepository.existsById(id)) {
            hospitalRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
