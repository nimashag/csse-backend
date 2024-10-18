package com.medilink.api.controllers;

import com.medilink.api.models.Appointment;
import com.medilink.api.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@PathVariable String patientId, @RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.makeAppointment(appointment, patientId);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable String patientId, @PathVariable String appointmentId, @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, appointment, patientId);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String patientId, @PathVariable String appointmentId) {
        appointmentService.deleteAppointment(appointmentId, patientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable String patientId, @PathVariable String appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId, patientId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments(@PathVariable String patientId) {
        List<Appointment> appointments = appointmentService.getAllAppointments(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}
