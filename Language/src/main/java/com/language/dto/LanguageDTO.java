package com.language.dto;

import com.language.enums.LanguageLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class LanguageDTO {

    private Integer id;

    private String name;

    private LanguageLevel languageLevel;
}
