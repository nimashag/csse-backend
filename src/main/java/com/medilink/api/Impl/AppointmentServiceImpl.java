package com.medilink.api.Impl;

import com.medilink.api.models.Appointment;
import com.medilink.api.repositories.AppointmentRepository;
import com.medilink.api.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment makeAppointment(Appointment appointment, String patientId) {
        appointment.setPatientId(patientId);  // Ensure the appointment is linked to the patient
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(String appointmentId, Appointment appointment, String patientId) {
        // Check if the appointment exists
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Update the existing appointment's details
        existingAppointment.setDate(appointment.getDate());
        existingAppointment.setTime(appointment.getTime());
        existingAppointment.setDoctorId(appointment.getDoctorId());
        existingAppointment.setHospitalId(appointment.getHospitalId());
        // ... (update other fields as necessary)

        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(String appointmentId, String patientId) {
        // Check if the appointment exists
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Ensure the appointment belongs to the patient before deleting
        if (!existingAppointment.getPatientId().equals(patientId)) {
            throw new RuntimeException("You do not have permission to delete this appointment");
        }

        appointmentRepository.delete(existingAppointment);
    }

    @Override
    public Appointment getAppointmentById(String appointmentId, String patientId) {
        Appointment existingAppointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Ensure the appointment belongs to the patient
        if (!existingAppointment.getPatientId().equals(patientId)) {
            throw new RuntimeException("You do not have permission to view this appointment");
        }

        return existingAppointment;
    }

    @Override
    public List<Appointment> getAllAppointments(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAllAppointmentsByDoctorId(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}
