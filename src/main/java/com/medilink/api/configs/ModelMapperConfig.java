package com.medilink.api.configs;

import com.medilink.api.dto.appointment.AppointmentRequestDTO;
import com.medilink.api.models.Appointment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT); // You can choose other strategies if necessary
        // Ignore the appointmentId property during mapping
        modelMapper.typeMap(AppointmentRequestDTO.class, Appointment.class)
                .addMappings(mapper -> mapper.skip(Appointment::setAppointmentId));
        return modelMapper;
    }
}
