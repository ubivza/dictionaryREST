package ru.aleksandr.dictionaryrest.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.aleksandr.dictionaryrest.dto.EnglishTranslateWordDTO;
import ru.aleksandr.dictionaryrest.dto.EnglishWordDTO;
import ru.aleksandr.dictionaryrest.entity.EnglishTranslateWord;
import ru.aleksandr.dictionaryrest.entity.EnglishWord;
import ru.aleksandr.dictionaryrest.service.EnglishDictionaryService;
import ru.aleksandr.dictionaryrest.util.errormodel.EnglishWordErrorResponse;
import ru.aleksandr.dictionaryrest.util.exception.EnglishWordNotCreatedException;
import ru.aleksandr.dictionaryrest.util.exception.EnglishWordNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eng-ru-dict")
public class EngRuDictRESTController {
    private final EnglishDictionaryService englishDictionaryService;
    private final ModelMapper modelMapper;

    public EngRuDictRESTController(EnglishDictionaryService englishDictionaryService, ModelMapper modelMapper) {
        this.englishDictionaryService = englishDictionaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<EnglishWordDTO> getAllWords() {
        List<EnglishWord> listToConvert = englishDictionaryService.showAll();

        return listToConvert.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/getK/{key}")
    public EnglishWordDTO getByKey(@PathVariable String key) {
        return this.convertToDto(englishDictionaryService.showByKey(key));
    }

    @GetMapping("/getV/{value}")
    public List<EnglishWordDTO> getByValue(@PathVariable String value) {
        List<EnglishWord> listToConvert = englishDictionaryService.showByValue(value);
        return listToConvert.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addWord(@RequestBody @Valid EnglishWordDTO englishWordDTO,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer error = new StringBuffer();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.stream().map(value -> error.append(value.getField())
                    .append(" - ")
                    .append(value.getDefaultMessage())
                    .append(";"));
            throw new EnglishWordNotCreatedException(error.toString());
        }

        EnglishWord englishWord = this.convertToBaseEntity(englishWordDTO);
        englishDictionaryService.save(englishWord);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/edit/{key}")
    public ResponseEntity<HttpStatus> editWord(@PathVariable String key,
                                               @RequestBody @Valid EnglishWordDTO englishWordDTO,
                                               BindingResult bindingResult) {


        //Протестить метод, не уверен


        if (bindingResult.hasErrors()) {
            StringBuffer error = new StringBuffer();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.stream().map(value -> error.append(value.getField())
                    .append(" - ")
                    .append(value.getDefaultMessage())
                    .append(";"));
            //Написать новый эксепшн
            throw new EnglishWordNotCreatedException(error.toString());
        }

        EnglishWord englishWord = englishDictionaryService.showByKey(key);
        EnglishWord fromUser = this.convertToBaseEntity(englishWordDTO);
        englishWord.setWord(fromUser.getWord());
        englishWord.setEnglishTranslateWords(fromUser.getEnglishTranslateWords());
        englishDictionaryService.update(englishWord);

        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<HttpStatus> deleteWord(@PathVariable String key) {
        englishDictionaryService.deleteByKey(key);
        return ResponseEntity.ok(HttpStatus.GONE);
    }

    @ExceptionHandler(EnglishWordNotFoundException.class)
    private ResponseEntity<EnglishWordErrorResponse> handle(EnglishWordNotFoundException exception) {
        EnglishWordErrorResponse response = new EnglishWordErrorResponse("No such word");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnglishWordNotCreatedException.class)
    private ResponseEntity<EnglishWordErrorResponse> handle(EnglishWordNotCreatedException exception) {
        EnglishWordErrorResponse response = new EnglishWordErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private EnglishWordDTO  convertToDto(EnglishWord englishWord) {
        EnglishWordDTO englishWordDTO = modelMapper.map(englishWord, EnglishWordDTO.class);
        return englishWordDTO;
    }

    private EnglishWord convertToBaseEntity(EnglishWordDTO englishWordDTO) {
        EnglishWord englishWord = modelMapper.map(englishWordDTO, EnglishWord.class);
        englishWord.setEnglishTranslateWords(englishWordDTO.getEnglishTranslateWordList()
                .stream().map(value -> convertToChildEntity(value, englishWord))
                .collect(Collectors.toList()));
        return englishWord;
    }
    private EnglishTranslateWord convertToChildEntity(EnglishTranslateWordDTO translateWordDTO,
                                                      EnglishWord englishWord) {
        EnglishTranslateWord englishTranslateWord = modelMapper.map(translateWordDTO, EnglishTranslateWord.class);
        englishTranslateWord.setEnglishWord(englishWord);
        return englishTranslateWord;
    }
}
