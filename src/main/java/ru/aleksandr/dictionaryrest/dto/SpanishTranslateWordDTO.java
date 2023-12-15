package ru.aleksandr.dictionaryrest.dto;

import lombok.Data;
import ru.aleksandr.dictionaryrest.entity.SpanishWord;

@Data
public class SpanishTranslateWordDTO {
    private String translation;
}
