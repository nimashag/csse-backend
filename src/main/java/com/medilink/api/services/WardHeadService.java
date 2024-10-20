package com.medilink.api.services;

import com.medilink.api.models.WardHead;
import com.medilink.api.repositories.WardHeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WardHeadService {

    private final WardHeadRepository wardHeadRepository;

    @Autowired
    public WardHeadService(WardHeadRepository wardHeadRepository) {
        this.wardHeadRepository = wardHeadRepository;
    }

    //create WardHead
    public WardHead saveWardHead(WardHead wardHead) {
        return wardHeadRepository.save(wardHead);
    }

    //get All WardHead
    public List<WardHead> getAllWardHead() {
        return wardHeadRepository.findAll();
    }

    //get WardHead using the Id
    public WardHead getWardHead(String id) {
        return wardHeadRepository.findById(id).orElse(null);
    }

    //update WardHead's data
    public WardHead updateWardHead(String id, WardHead wardHead) {
        Optional<WardHead> existingWardHead = wardHeadRepository.findById(id);
        if(existingWardHead.isPresent()) {
            wardHead.setId(id);
            return wardHeadRepository.save(wardHead);
        }else {
            return null;
        }
    }

    //delete WardHead
    public boolean deleteWardHead(String id) {
        if (wardHeadRepository.existsById(id)) {
            wardHeadRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
