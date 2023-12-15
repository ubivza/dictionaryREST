package ru.aleksandr.dictionaryrest.service;

import org.springframework.stereotype.Component;
import ru.aleksandr.dictionaryrest.entity.SpanishTranslateWord;
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

    public void saveString(String word) {
        String[] arr = word.split(" ", 2);
        String[] arrWords = new String[0];
        if (arr[1].contains(", ")) {
            arrWords = arr[1].split(", ");
        }

        SpanishWord spanishWord = new SpanishWord();
        spanishWord.setWord(arr[0]);

        if (arr.length == 2) {
            if (arr[1].isEmpty()) {
                spanishWord.setSpanishTranslateWords(null);
                return;
            }
            SpanishTranslateWord spanishTranslateWord = new SpanishTranslateWord();

            spanishTranslateWord.setTranslation(arr[1]);
            spanishTranslateWord.setSpanishWord(spanishWord);
            List<SpanishTranslateWord> list = new ArrayList<>();
            list.add(spanishTranslateWord);
            spanishWord.setSpanishTranslateWords(list);
        }
        if (arrWords.length != 0) {
            List<SpanishTranslateWord> wordList = new ArrayList<>();
            for (int i = 0; i < arrWords.length; i++) {
                SpanishTranslateWord spanishTranslateWord = new SpanishTranslateWord();

                spanishTranslateWord.setTranslation(arrWords[i]);
                spanishTranslateWord.setSpanishWord(spanishWord);
                wordList.add(spanishTranslateWord);
            }

            spanishWord.setSpanishTranslateWords(wordList);
        }
        spanishRuRepository.save(spanishWord);
    }

    public void deleteById(Long id) {
        spanishRuRepository.deleteById(id);
    }

    public void updateById(Long valueOf, String word) {
        SpanishWord spanishWord = spanishRuRepository.findById(valueOf).get();

        String[] arr = word.split(" ", 2);
        String[] arrWords = new String[0];
        if (arr[1].contains(", ")) {
            arrWords = arr[1].split(", ");
        }


        if (arr.length == 2) {
            if (arr[1].isEmpty()) {
                spanishWord.setSpanishTranslateWords(null);
                return;
            }
            SpanishTranslateWord spanishTranslateWord = new SpanishTranslateWord();

            spanishTranslateWord.setTranslation(arr[1]);
            spanishTranslateWord.setSpanishWord(spanishWord);
            List<SpanishTranslateWord> list = new ArrayList<>();
            list.add(spanishTranslateWord);
            spanishWord.setSpanishTranslateWords(list);
        }

        if (arrWords.length != 0) {
            List<SpanishTranslateWord> wordList = new ArrayList<>();
            for (int i = 0; i < arrWords.length; i++) {
                SpanishTranslateWord spanishTranslateWord = new SpanishTranslateWord();

                spanishTranslateWord.setTranslation(arrWords[i]);
                spanishTranslateWord.setSpanishWord(spanishWord);
                wordList.add(spanishTranslateWord);
            }

            spanishWord.setSpanishTranslateWords(wordList);
        }
        spanishRuRepository.save(spanishWord);
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
}
