package com.medilink.api.services;

import com.medilink.api.models.Patient;
import com.medilink.api.models.User;
import com.medilink.api.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getPatient(String id) {
        return patientRepository.findById(id).orElse(null);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient updatePatient(String id, Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isPresent()) {
            patient.setId(id);
            return patientRepository.save(patient);
        } else {
            return null;
        }
    }

    public void deletePatient(String id) {
        if(patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
        }
    }


}
