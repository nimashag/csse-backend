package com.medilink.api.services;

import com.medilink.api.dto.clinic.ClinicRequestDTO;
import com.medilink.api.models.Clinic;
import com.medilink.api.models.Doctor;
import com.medilink.api.models.Hospital;
import com.medilink.api.models.Patient;
import com.medilink.api.repositories.ClinicRepository;
import com.medilink.api.repositories.DoctorRepository;
import com.medilink.api.repositories.HospitalRepository;
import com.medilink.api.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Clinic saveClinic(ClinicRequestDTO clinicRequestDTO) {

        // Fetch doctor and hospital by their IDs
        Doctor doctor = doctorRepository.findById(clinicRequestDTO.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Hospital hospital = hospitalRepository.findById(clinicRequestDTO.getHospitalId())
                .orElseThrow(() -> new IllegalArgumentException("Hospital not found"));

        // Create and populate the Clinic object
        Clinic clinic = new Clinic();
        clinic.setClinicName(clinicRequestDTO.getClinicName());
        clinic.setDoctor(doctor);       // Set the doctor object
        clinic.setHospital(hospital);   // Set the hospital object
        clinic.setPatients(clinicRequestDTO.getPatients());  // Patients (if any)

        // Save the clinic to the database
        return clinicRepository.save(clinic);
    }


    public Clinic addPatientToClinic(String clinicId, String patientId) {

        // Fetch the clinic by its ID
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new IllegalArgumentException("Clinic not found"));

        // Fetch the patient by its ID
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        // Add the patient to the clinic's patient list
        List<Patient> patients = clinic.getPatients();
        if (patients == null) {
            patients = new ArrayList<>();
        }
        patients.add(patient);  // Add the new patient to the list

        // Update the clinic with the new patient list
        clinic.setPatients(patients);

        // Save the updated clinic back to the database
        return clinicRepository.save(clinic);
    }

    public List<Clinic> getClinics() {
        return clinicRepository.findAll();
    }

    public Clinic getOneClinic(String id){
        return clinicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Clinic not found"));
    }

    public List<Clinic> getClinicsByPatientId(String patientId) {
        List<Clinic> allClinics = clinicRepository.findAll();
        // Filter clinics where the patient list contains the given patient ID
        return allClinics.stream()
                .filter(clinic -> clinic.getPatients().stream()
                        .anyMatch(patient -> patient.getId().equals(patientId)))
                .collect(Collectors.toList());
    }

}
