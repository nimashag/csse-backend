package com.medilink.api.controllers;

import com.medilink.api.dto.appointment.AppointmentRequestDTO;
import com.medilink.api.dto.appointment.AppointmentResponseDTO;
import com.medilink.api.models.Appointment;
import com.medilink.api.services.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients/{patientId}/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@PathVariable String patientId, @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = modelMapper.map(appointmentRequestDTO, Appointment.class);
        Appointment createdAppointment = appointmentService.makeAppointment(appointment, patientId);
        AppointmentResponseDTO responseDTO = modelMapper.map(createdAppointment, AppointmentResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable String patientId, @PathVariable String appointmentId, @RequestBody AppointmentRequestDTO appointmentRequestDTO) {
        Appointment appointment = modelMapper.map(appointmentRequestDTO, Appointment.class);
        Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, appointment, patientId);
        if (updatedAppointment == null) {
            return ResponseEntity.notFound().build();
        }
        AppointmentResponseDTO responseDTO = modelMapper.map(updatedAppointment, AppointmentResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String patientId, @PathVariable String appointmentId) {
        appointmentService.deleteAppointment(appointmentId, patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable String patientId, @PathVariable String appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId, patientId);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        AppointmentResponseDTO responseDTO = modelMapper.map(appointment, AppointmentResponseDTO.class);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments(@PathVariable String patientId) {
        List<Appointment> appointments = appointmentService.getAllAppointments(patientId);
        List<AppointmentResponseDTO> responseDTOs = appointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
