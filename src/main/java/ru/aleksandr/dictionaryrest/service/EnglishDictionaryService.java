package ru.aleksandr.dictionaryrest.service;

import org.springframework.stereotype.Service;
import ru.aleksandr.dictionaryrest.entity.EnglishWord;
import ru.aleksandr.dictionaryrest.repository.EngRuRepository;
import ru.aleksandr.dictionaryrest.util.exception.EnglishWordNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class EnglishDictionaryService {

    private final EngRuRepository engRuRepository;

    public EnglishDictionaryService(EngRuRepository engRuRepository) {
        this.engRuRepository = engRuRepository;
    }


    public List<EnglishWord> showAll() {
        return engRuRepository.getAll();
    }

    public void save(EnglishWord englishWord) {
        engRuRepository.save(englishWord);
    }

    public EnglishWord showByKey(String key) {
        Optional<EnglishWord> value = engRuRepository.getByKey(key);
        return value.orElseThrow(EnglishWordNotFoundException::new);
    }

    public List<EnglishWord> showByValue(String value) {
        return engRuRepository.getByValue(value).orElseThrow(EnglishWordNotFoundException::new);
    }

    public void deleteByKey(String deleteWord) {
        engRuRepository.deleteByKey(deleteWord);
    }

    public void updateById(Long id, EnglishWord englishWord) {
        EnglishWord wordToUpdate = engRuRepository.getById(id).orElseThrow(EnglishWordNotFoundException::new);
        wordToUpdate.setWord(englishWord.getWord());
        wordToUpdate.setEnglishTranslateWords(englishWord.getEnglishTranslateWords());
        engRuRepository.update(wordToUpdate);
    }

    public void deleteById(Long id) {
        engRuRepository.deleteById(id);
    }

    public void update(EnglishWord englishWord) {
        engRuRepository.update(englishWord);
    }
}