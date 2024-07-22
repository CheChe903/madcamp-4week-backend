package com.example.madcamp_4week.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Accord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="accordId")
    private Long id;

    @Column(name = "accordName")
    private String accordName;

    @Column(name = "accordKoreanName")
    private String accordKoreanName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atmosphereId")
    private Atmosphere atmosphere;

    @Column(name = "accordImageUrl")
    private String accordImageUrl;

    @Column(name = "accordExplanation")
    private String accordExplanation;

    @Builder
    public Accord(String accordName, String accordKoreanName, String accordExplanation, String accordImageUrl, Atmosphere atmosphere) {
        this.accordName = accordName;
        this.accordKoreanName = accordKoreanName;
        this.accordExplanation = accordExplanation;
        this.accordImageUrl = accordImageUrl;
        this.atmosphere = atmosphere;
    }
}
