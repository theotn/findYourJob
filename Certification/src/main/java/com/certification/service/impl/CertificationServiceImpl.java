package com.certification.service.impl;

import com.certification.dto.CertificationDTO;
import com.certification.entity.Certification;
import com.certification.repository.CertificationRepository;
import com.certification.service.CertificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CertificationServiceImpl implements CertificationService {

    private CertificationRepository certificationRepository;
    private ModelMapper modelMapper;

    public CertificationServiceImpl(CertificationRepository certificationRepository, ModelMapper modelMapper) {
        this.certificationRepository = certificationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CertificationDTO createCertification(CertificationDTO certificationDTO) {

        Certification certification = modelMapper.map(certificationDTO, Certification.class);
        certificationRepository.save(certification);
        return modelMapper.map(certification, CertificationDTO.class);

    }
}
