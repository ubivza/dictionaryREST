package ru.aleksandr.dictionaryrest.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "spanish_translate")
@Data
public class SpanishTranslateWord {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spanish_word_id", referencedColumnName = "id")
    private SpanishWord spanishWord;
    @Column(name = "translate_word")
    private String translation;

    public SpanishTranslateWord() {}

}
