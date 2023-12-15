package ru.aleksandr.dictionaryrest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class SpanishWordDTO {
    @NotBlank(message = "Word shouldnt be empty")
    @Pattern(regexp = "[A-Z, a-z]{4}", message = "Word must be 4 characters long and should contain only latin letters")
    private String word;
    @JsonProperty("translations")
    private List<SpanishTranslateWordDTO> spanishTranslateWordList;
}
