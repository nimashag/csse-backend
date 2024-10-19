package com.medilink.api.services;

import com.medilink.api.models.Receptionist;
import com.medilink.api.repositories.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;

    @Autowired
    public ReceptionistService(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    //create receptionist
    public Receptionist saveReceptionist(Receptionist receptionist) {
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
        if(existingReceptionist.isPresent()) {
            receptionist.setId(id);
            return receptionistRepository.save(receptionist);
        }else {
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


}
