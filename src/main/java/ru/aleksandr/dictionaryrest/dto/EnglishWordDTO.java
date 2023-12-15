package ru.aleksandr.dictionaryrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class EnglishWordDTO {
    @Max(value = 99999, message = "Word should be 5 digits in length")
    @NotNull(message = "Word shouldnt be empty")
    private Integer word;
    @JsonProperty("translations")
    private List<EnglishTranslateWordDTO> englishTranslateWordList;

}
