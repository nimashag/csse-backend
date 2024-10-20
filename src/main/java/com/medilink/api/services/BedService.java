package com.medilink.api.services;

import com.medilink.api.dto.bed.BedRequestDTO;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.enums.BedType;
import com.medilink.api.models.Bed;
import com.medilink.api.models.Patient;
import com.medilink.api.repositories.BedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BedService {
    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private PatientService patientService;

    public List<Bed> createBeds(String wardId, int numberOfBeds) {
        List<Bed> beds = new ArrayList<>();

        for (int i = 0; i < numberOfBeds; i++) {
            Bed bed = new Bed();
            bed.setWardId(wardId);
            bed.setStatus(BedType.AVAILABLE);
            beds.add(bed);
        }

        return bedRepository.saveAll(beds);
    }

    public List<Bed> findBedsByWardId(String wardId) {
        return bedRepository.findByWardId(wardId);
    }

    public Bed updateBed(BedRequestDTO bedRequestDTO) {

        Bed existingBed = bedRepository.findById(bedRequestDTO.getBedId())
                .orElseThrow(() -> new IllegalArgumentException("Bed not found"));


        if (bedRequestDTO.getPatientId() != null) {
            PatientResponseDTO patient = patientService.getPatientById(bedRequestDTO.getPatientId());
            existingBed.setPatient(patient);
            existingBed.setStatus(BedType.RECEIVED);
        } else {

            existingBed.setPatient(null);
            existingBed.setStatus(BedType.AVAILABLE);
        }

        return bedRepository.save(existingBed);
    }

    public Bed emptyBed(String id) {
        Bed existingBed = bedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bed not found"));

            existingBed.setPatient(null);
            existingBed.setStatus(BedType.AVAILABLE);

        return bedRepository.save(existingBed);
    }

    public boolean deleteBed(String id) {
        if (bedRepository.existsById(id)) {
            bedRepository.deleteById(id);
            return true; // Successful deletion
        }
        return false; // Bed not found
    }



}
