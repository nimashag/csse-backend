package com.medilink.api.services;

import com.medilink.api.dto.ward.WardRequestDTO;
import com.medilink.api.dto.ward.WardResponseDTO;
import com.medilink.api.models.Bed;
import com.medilink.api.models.Hospital;
import com.medilink.api.models.Ward;
import com.medilink.api.repositories.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class WardService {
    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private BedService bedService;

    public WardResponseDTO createWard(WardRequestDTO request) {


        Hospital hospital = hospitalService.getHospitalById(request.getHospitalId());
        if (hospital == null) {
            throw new IllegalArgumentException("Hospital not found");
        }


        Ward ward = new Ward();
        ward.setWardNo(request.getWardNo());
        ward.setHospital(hospital);
        Ward savedWard = wardRepository.save(ward);


        List<Bed> beds = bedService.createBeds(savedWard.getWardId(), request.getBeds());


        savedWard.setBeds(beds);


        WardResponseDTO responseDTO = new WardResponseDTO();
        responseDTO.setWardId(savedWard.getWardId());
        responseDTO.setHospital(savedWard.getHospital());
        responseDTO.setWardNo(savedWard.getWardNo());
        responseDTO.setBeds(beds);

        return responseDTO;
    }

    public Ward getWard(String wardId) {
        return wardRepository.findById(wardId)
                .orElseThrow(() -> new IllegalArgumentException("Ward not found"));
    }

    public List<Ward> getHospitalWard(String hospitalId) {
        return wardRepository.findByHospital_HospitalId(hospitalId);
    }

    public boolean deleteWard(String id) {
        if (wardRepository.existsById(id)) {
            wardRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}