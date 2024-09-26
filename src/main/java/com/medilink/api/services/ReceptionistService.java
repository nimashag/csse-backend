package com.medilink.api.services;

import com.medilink.api.models.Receptionist;
import com.medilink.api.repositories.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ReceptionistService(ReceptionistRepository receptionistRepository, PasswordEncoder passwordEncoder) {
        this.receptionistRepository = receptionistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //create receptionist
    public Receptionist saveReceptionist(Receptionist receptionist) {
        // Encode password before saving
        receptionist.setPassword(passwordEncoder.encode(receptionist.getPassword()));
        return receptionistRepository.save(receptionist);
    }

    //get All Receptionists
    public List<Receptionist> getAllReceptionists() {
        return receptionistRepository.findAll();
    }

    //get receptionist using the Id
    public Receptionist getReceptionist(String id) {
        return receptionistRepository.findById(id).orElse(null);
    }

    //update receptionist's data
    public Receptionist updateReceptionist(String id, Receptionist receptionist) {
        Optional<Receptionist> existingReceptionist = receptionistRepository.findById(id);
        if (existingReceptionist.isPresent()) {
            if (receptionist.getPassword() == null) {
                receptionist.setPassword(existingReceptionist.get().getPassword());
            } else {
                receptionist.setPassword(passwordEncoder.encode(receptionist.getPassword()));
            }
            receptionist.setId(id);
            return receptionistRepository.save(receptionist);
        } else {
            return null;
        }
    }

    //delete receptionist
    public boolean deleteReceptionist(String id) {
        if (receptionistRepository.existsById(id)) {
            receptionistRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //authenticates a receptionist by email and password
    public Receptionist authenticateReceptionist(String email, String password) {
        Optional<Receptionist> receptionistOptional = receptionistRepository.findByEmail(email);
        if (receptionistOptional.isPresent()) {
            Receptionist receptionist = receptionistOptional.get();
            if (passwordEncoder.matches(password, receptionist.getPassword())) {
                return receptionist;
            }
        }
        return null; // Return null if authentication fails
    }
}
