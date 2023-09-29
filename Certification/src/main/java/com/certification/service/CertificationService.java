package com.certification.service;

import com.certification.dto.CertificationDTO;
import com.certification.exception.NotFoundException;

public interface CertificationService {

    CertificationDTO createCertification(CertificationDTO certificationDTO);

    CertificationDTO getCertification(Integer certificationId) throws NotFoundException;

    CertificationDTO updateCertification(Integer certificationId, CertificationDTO certificationDTO) throws NotFoundException;

    CertificationDTO deleteCertification(Integer certificationId) throws NotFoundException;
}

