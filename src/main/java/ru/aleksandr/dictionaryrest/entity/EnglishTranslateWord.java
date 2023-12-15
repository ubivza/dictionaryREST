package ru.aleksandr.dictionaryrest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "eng_translate")
@Data
public class EnglishTranslateWord {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eng_word_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonBackReference
    private EnglishWord englishWord;
    @Column(name = "translate_word")
    private String translation;

    public EnglishTranslateWord() {}
}
