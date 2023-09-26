package com.language.service;

import com.language.dto.LanguageDTO;
import com.language.entity.Language;
import com.language.exception.NotFoundException;

public interface LanguageService {


    LanguageDTO createLanguage(LanguageDTO languageDTO);
    LanguageDTO getLanguage(Integer languageId) throws NotFoundException;
    LanguageDTO updateLanguage(Integer languageId,LanguageDTO languageDTO) throws NotFoundException;
    LanguageDTO deleteLanguage(Integer languageId) throws NotFoundException;


}
