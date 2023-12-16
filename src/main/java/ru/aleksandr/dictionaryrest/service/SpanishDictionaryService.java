package ru.aleksandr.dictionaryrest.service;

import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryrest.entity.SpanishWord;
import ru.aleksandr.dictionaryrest.repository.SpanishRuRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class SpanishDictionaryService {

    private final SpanishRuRepository spanishRuRepository;

    public SpanishDictionaryService(SpanishRuRepository spanishRuRepository) {
        this.spanishRuRepository = spanishRuRepository;
    }

    public SpanishWord showByKey(String key) {
        return spanishRuRepository.getByKey(key);
    }

    public List<SpanishWord> showAll() {
        List<SpanishWord> list = new ArrayList<>();
        spanishRuRepository.findAll().forEach(s -> list.add(s));
        return list;
    }

    public List<SpanishWord> showByValue(String value) {
        return spanishRuRepository.findByValue(value);
    }

    public void deleteByKey(String key) {
        spanishRuRepository.deleteByWord(key);
    }

    public void save(SpanishWord spanishWord) {
        spanishRuRepository.save(spanishWord);
    }

    public void update(SpanishWord spanishWord) {
        spanishRuRepository.save(spanishWord);
    }
}
