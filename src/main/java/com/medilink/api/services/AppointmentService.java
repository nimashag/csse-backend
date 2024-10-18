package com.medilink.api.services;

import com.medilink.api.models.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment makeAppointment(Appointment appointment, String patientId);
    Appointment updateAppointment(String appointmentId, Appointment appointment, String patientId);
    void deleteAppointment(String appointmentId, String patientId);
    Appointment getAppointmentById(String appointmentId, String patientId);
    List<Appointment> getAllAppointments(String patientId);
}
