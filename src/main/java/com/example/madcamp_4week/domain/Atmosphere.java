package com.example.madcamp_4week.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Atmosphere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="atmosphereId")
    private Long id;

    @Column(name ="atmosphereName")
    private String atmosphereName;

    @Column(name = "atmosphereKoreanName")
    private String atmosphereKoreanName;

    @Column(name ="atmosphereImageUrl")
    private String atmosphereImageUrl;

    @Column(name = "atmosphereExplanation")
    private String atmosphereExplanation;

    @OneToMany(mappedBy = "atmosphere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Accord> accords;


}
