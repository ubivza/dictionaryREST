package ru.aleksandr.dictionaryrest.util.errormodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpanishWordErrorResponse {
    private String message;
}
