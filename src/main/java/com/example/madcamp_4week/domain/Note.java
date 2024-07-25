package com.example.madcamp_4week.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="noteId")
    private Long id;

    @Column(name = "noteName")
    private String noteName;

    @Column(name = "noteKoreanName")
    private String noteKoreanName;

    @Column(name = "noteExplanation")
    private String noteExplanation;
    @Builder
    public Note(String noteName, String noteKoreanName, String noteExplanation) {
        this.noteName = noteName;
        this.noteKoreanName=noteKoreanName;
        this.noteExplanation=noteExplanation;
    }
}
