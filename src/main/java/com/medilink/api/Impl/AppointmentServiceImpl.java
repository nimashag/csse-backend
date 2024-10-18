package com.medilink.api.Impl;

import com.medilink.api.models.Appointment;
import com.medilink.api.models.Patient;
import com.medilink.api.repositories.PatientRepository;
import com.medilink.api.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Appointment makeAppointment(Appointment appointment, String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.getAppointments().add(appointment);
        patientRepository.save(patient);
        return appointment;
    }

    @Override
    public Appointment updateAppointment(String appointmentId, Appointment updatedAppointment, String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.getAppointments().stream()
                .filter(appointment -> appointment.getAppointmentId().equals(appointmentId))
                .findFirst()
                .ifPresent(appointment -> {
                    appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
                    appointment.setHospitalId(updatedAppointment.getHospitalId());
                    appointment.setStatus(updatedAppointment.getStatus());
                });
        patientRepository.save(patient);
        return updatedAppointment;
    }

    @Override
    public void deleteAppointment(String appointmentId, String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.getAppointments().removeIf(appointment -> appointment.getAppointmentId().equals(appointmentId));
        patientRepository.save(patient);
    }

    @Override
    public Appointment getAppointmentById(String appointmentId, String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return patient.getAppointments().stream()
                .filter(appointment -> appointment.getAppointmentId().equals(appointmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public List<Appointment> getAllAppointments(String patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return patient.getAppointments();
    }
}
