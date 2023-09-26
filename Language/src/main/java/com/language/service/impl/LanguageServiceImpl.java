package com.language.service.impl;

import com.language.dto.LanguageDTO;
import com.language.entity.Language;
import com.language.exception.NotFoundException;
import com.language.repository.LanguageRepository;
import com.language.service.LanguageService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {


    private LanguageRepository languageRepository;

    private ModelMapper modelMapper;

    public LanguageServiceImpl(LanguageRepository languageRepository, ModelMapper modelMapper) {
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LanguageDTO createLanguage(LanguageDTO languageDTO) {


        Language language = modelMapper.map(languageDTO,Language.class);
        languageRepository.save(language);

        return modelMapper.map(language,LanguageDTO.class);
    }

    @Override
    public LanguageDTO getLanguage(Integer languageId) throws NotFoundException {

        Optional<Language> languageOptional = languageRepository.findById(languageId);
        Language language = languageOptional.orElseThrow(()-> new NotFoundException("Not found!"));

        return modelMapper.map(language,LanguageDTO.class);
    }

    @Override
    public LanguageDTO updateLanguage(Integer languageId, LanguageDTO languageDTO) throws NotFoundException {

        Optional<Language> languageOptional = languageRepository.findById(languageId);
        Language language = languageOptional.orElseThrow(()-> new NotFoundException("Not found!"));

        if(languageDTO.getName()!=null) language.setName(languageDTO.getName());
        if(languageDTO.getLanguageLevel()!=null) language.setLanguageLevel(languageDTO.getLanguageLevel());

        return modelMapper.map(language,LanguageDTO.class);
    }

    @Override
    public LanguageDTO deleteLanguage(Integer languageId) throws NotFoundException {

       Optional<Language> languageOptional = languageRepository.findById(languageId);
       Language language = languageOptional.orElseThrow(()-> new NotFoundException("Not found!"));

       languageRepository.delete(language);

       return modelMapper.map(language,LanguageDTO.class);
    }
}
