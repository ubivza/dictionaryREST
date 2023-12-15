package ru.aleksandr.dictionaryrest.dao;



import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.aleksandr.dictionaryrest.entity.EnglishWord;
import ru.aleksandr.dictionaryrest.repository.EngRuRepository;

import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class EnglishDictionaryDAO implements EngRuRepository {

    private final EntityManager entityManager;

    public EnglishDictionaryDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<EnglishWord> getAll() {
        return entityManager.createQuery("from EnglishWord", EnglishWord.class).getResultList();
    }

    @Override
    @Transactional
    public void save(EnglishWord ew) {
        entityManager.persist(ew);
    }

    @Override
    @Transactional
    public void update(EnglishWord ew) {
        entityManager.merge(ew);
    }

    @Override
    @Transactional
    public void deleteByKey(String key) {

        Query query = entityManager.createQuery("delete from EnglishWord where word=:key");
        query.setParameter("key", key);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public Optional<EnglishWord> getByKey(String key) {

        Query query = entityManager.createQuery("from EnglishWord where word=:key");
        query.setParameter("key", key);
        EnglishWord englishWord = null;
        try {
            englishWord = (EnglishWord) query.getSingleResult();
        } catch (NoResultException e) {
            log.warn(e + " caught while searching " + key + " in database");
        }
        return Optional.ofNullable(englishWord);
    }

    @Transactional
    @Override
    public Optional<List<EnglishWord>> getByValue(String value) {

        Query query = entityManager.createQuery("from EnglishWord e join e.englishTranslateWords t where t.translation=:translateWord");
        query.setParameter("translateWord", value);

        return Optional.ofNullable(query.getResultList());
    }

    @Transactional
    @Override
    public Optional<EnglishWord> getById(Long id) {
        return Optional.ofNullable(entityManager.find(EnglishWord.class, id));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        Query query = entityManager.createQuery("delete from EnglishWord where id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
