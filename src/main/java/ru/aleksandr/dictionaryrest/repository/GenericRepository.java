package ru.aleksandr.dictionaryrest.repository;


import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, KEY> {
    List<T> getAll();
    Optional<T> getByKey(KEY key);
    void save(T t);
    void update(T t);
    void deleteByKey(KEY key);
     Optional<List<T>> getByValue(String value);
    Optional<T> getById(Long id);
    void deleteById(Long id);
}
