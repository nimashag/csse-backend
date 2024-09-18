package com.medilink.api.controllers;

import com.medilink.api.services.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateQRCode(@RequestParam String text) {
        try {
            byte[] qrImage = qrCodeService.generateQRCodeImage(text, 350, 350);
            String qrImageBase64 = Base64.getEncoder().encodeToString(qrImage);
            return new ResponseEntity<>(qrImageBase64, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
