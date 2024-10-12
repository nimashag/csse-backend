package com.medilink.api.controllers;

import com.medilink.api.dto.patient.PatientRequestDTO;
import com.medilink.api.dto.patient.PatientResponseDTO;
import com.medilink.api.enums.UserType;
import com.medilink.api.models.Doctor;
import com.medilink.api.models.Patient;
import com.medilink.api.services.DoctorService;
import com.medilink.api.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    public DoctorController(DoctorService doctorService, ModelMapper modelMapper) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
    }

    //create doctor
    @PostMapping("/")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

}
