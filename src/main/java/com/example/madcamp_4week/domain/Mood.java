package com.example.madcamp_4week.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="moodId")
    private Long id;

    @Column(name ="moodName")
    private String moodName;

    @Column(name = "moodKoreanName")
    private String moodKoreanName;

    @Column(name ="moodImageUrl")
    private String moodImageUrl;

    @Column(name = "moodExplanation")
    private String moodExplanation;

    @Column(name = "moodColorUrl")
    private String moodColorUrl;

    @OneToMany(mappedBy = "mood", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Accord> accords;


}
