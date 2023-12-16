package ru.aleksandr.dictionaryrest.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.aleksandr.dictionaryrest.dto.SpanishTranslateWordDTO;
import ru.aleksandr.dictionaryrest.dto.SpanishWordDTO;
import ru.aleksandr.dictionaryrest.entity.SpanishTranslateWord;
import ru.aleksandr.dictionaryrest.entity.SpanishWord;
import ru.aleksandr.dictionaryrest.service.SpanishDictionaryService;
import ru.aleksandr.dictionaryrest.util.exception.EnglishWordNotCreatedException;
import ru.aleksandr.dictionaryrest.util.exception.SpanishWordNotCreatedException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/spain-ru-dict")
public class SpainRuDictRESTController {

    private final SpanishDictionaryService spanishDictionaryService;
    private final ModelMapper modelMapper;

    public SpainRuDictRESTController(SpanishDictionaryService spanishDictionaryService, ModelMapper modelMapper) {
        this.spanishDictionaryService = spanishDictionaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<SpanishWordDTO> getAllWords() {
        List<SpanishWord> listToConvert = spanishDictionaryService.showAll();
        return listToConvert.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/getK/{key}")
    public SpanishWordDTO getByKey(@PathVariable String key) {
        return this.convertToDto(spanishDictionaryService.showByKey(key));
    }

    @GetMapping("/getV/{value}")
    public List<SpanishWordDTO> getByValue(@PathVariable String value) {
        List<SpanishWord> listToConvert = spanishDictionaryService.showByValue(value);
        return listToConvert.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addWord(@RequestBody @Valid SpanishWordDTO spanishWordDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer error = new StringBuffer();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.stream().map(value -> error.append(value.getField())
                    .append(" - ")
                    .append(value.getDefaultMessage())
                    .append(";"));
            throw new SpanishWordNotCreatedException(error.toString());
        }

        SpanishWord spanishWord = this.convertToBaseEntity(spanishWordDTO);
        spanishDictionaryService.save(spanishWord);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/edit/{key}")
    public ResponseEntity<HttpStatus> editWord(@PathVariable String key,
                                               @RequestBody @Valid SpanishWordDTO spanishWordDTO,
                                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer error = new StringBuffer();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.stream().map(value -> error.append(value.getField())
                    .append(" - ")
                    .append(value.getDefaultMessage())
                    .append(";"));
            //change exception
            throw new SpanishWordNotCreatedException(error.toString());
        }

        SpanishWord spanishWord = spanishDictionaryService.showByKey(key);
        SpanishWord fromUser = this.convertToBaseEntity(spanishWordDTO);
        spanishWord.setWord(fromUser.getWord());
        spanishWord.setSpanishTranslateWords(fromUser.getSpanishTranslateWords());
        spanishDictionaryService.update(spanishWord);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<HttpStatus> deleteWord(@PathVariable String key) {
        spanishDictionaryService.deleteByKey(key);
        return ResponseEntity.ok(HttpStatus.GONE);
    }

    //make exceptionhandlers or another from baeldung

    private SpanishWord convertToBaseEntity(SpanishWordDTO spanishWordDTO) {
        SpanishWord spanishWord = modelMapper.map(spanishWordDTO, SpanishWord.class);
        spanishWord.setSpanishTranslateWords(spanishWordDTO.getSpanishTranslateWordList()
                .stream().map(value -> convertToChildEntity(value, spanishWord))
                .collect(Collectors.toList()));
        return spanishWord;
    }

    private SpanishTranslateWord convertToChildEntity(SpanishTranslateWordDTO value,
                                                      SpanishWord spanishWord) {
        SpanishTranslateWord spanishTranslateWord = modelMapper.map(value, SpanishTranslateWord.class);
        spanishTranslateWord.setSpanishWord(spanishWord);
        return spanishTranslateWord;
    }

    private SpanishWordDTO convertToDto(SpanishWord spanishWord) {
        SpanishWordDTO spanishWordDTO = modelMapper.map(spanishWord, SpanishWordDTO.class);
        return spanishWordDTO;
    }
}
