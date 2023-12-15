package ru.aleksandr.dictionaryrest.util.errormodel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnglishWordErrorResponse {
    private String message;

}
