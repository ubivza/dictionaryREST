package ru.aleksandr.dictionaryrest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "spanish_word")
@Data
public class SpanishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "word")
    @NotBlank(message = "Невозможно создать запись в словаре без иностранного слова")
    @Size(message = "Слово должно быть 4 латинских символа в длину")
    private String word;
    @OneToMany(mappedBy = "spanishWord", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SpanishTranslateWord> spanishTranslateWords;

    public SpanishWord() {}

}
