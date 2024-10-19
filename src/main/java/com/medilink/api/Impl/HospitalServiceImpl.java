package com.medilink.api.Impl;

import com.medilink.api.models.Hospital;
import com.medilink.api.repositories.HospitalRepository;
import com.medilink.api.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital getHospitalById(String id) {
        return hospitalRepository.findById(id).orElse(null);
    }

    @Override
    public Hospital updateHospital(String id, Hospital hospital) {
        Optional<Hospital> existingHospital = hospitalRepository.findById(id);
        if (existingHospital.isPresent()) {
            hospital.setHospitalId(id);  // Keep the existing ID
            return hospitalRepository.save(hospital);  // Update and save
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteHospital(String id) {
        if (hospitalRepository.existsById(id)) {
            hospitalRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
