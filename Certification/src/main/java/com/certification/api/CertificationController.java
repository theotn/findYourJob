package com.certification.api;


import com.certification.dto.CertificationDTO;
import com.certification.service.CertificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certification")
public class CertificationController {

    private CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }
    @PostMapping
    public ResponseEntity<CertificationDTO> createCertification(@RequestBody CertificationDTO certification){
        CertificationDTO certificationDTO = certificationService.createCertification(certification);
        return new ResponseEntity<>(certificationDTO, HttpStatus.CREATED);
    }
}
