package com.certification.api;


import com.certification.dto.CertificationDTO;
import com.certification.exception.NotFoundException;
import com.certification.service.CertificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certification")
public class CertificationController {

    private CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }
    @PostMapping
    public ResponseEntity<CertificationDTO> createCertification(@RequestBody CertificationDTO certificationDTO){
        CertificationDTO certification = certificationService.createCertification(certificationDTO);
        return new ResponseEntity<>(certification, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<CertificationDTO> getCertification(@RequestParam("certification") Integer certificationId) throws NotFoundException {
        CertificationDTO certificationDTO = certificationService.getCertification(certificationId);
        return new ResponseEntity<>(certificationDTO, HttpStatus.OK);
    }
    @PatchMapping
    public ResponseEntity<CertificationDTO> updateCertification(@RequestParam("certification") Integer certificationId, @RequestBody CertificationDTO certificationDTO) throws NotFoundException {
        CertificationDTO certification = certificationService.updateCertification(certificationId, certificationDTO);
        return new ResponseEntity<>(certification, HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<CertificationDTO> deleteCertification(@RequestParam("certification") Integer certificationId) throws NotFoundException {
        CertificationDTO certificationDTO = certificationService.deleteCertification(certificationId);
        return new ResponseEntity<>(certificationDTO, HttpStatus.OK);
    }
}
