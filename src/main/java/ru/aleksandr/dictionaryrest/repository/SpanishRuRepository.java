package ru.aleksandr.dictionaryrest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryrest.entity.SpanishWord;

import java.util.List;

@Repository
@Transactional
public interface SpanishRuRepository extends CrudRepository<SpanishWord, Long> {

    @Query("select s from SpanishWord s where s.word=?1")
    SpanishWord getByKey(String key);

    @Query("from SpanishWord s join s.spanishTranslateWords e where e.translation=?1")
    List<SpanishWord> findByValue(String value);

    void deleteByWord(String word);
}
