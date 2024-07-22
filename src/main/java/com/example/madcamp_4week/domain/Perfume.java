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
public class Perfume {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="perfumeId")
    private Long id;

    @Column(name = "perfumeName")
    private String perfumeName;

    @Column(name= "perfumeBrand")
    private String perfumeBrand;

    @Column(name = "perfumeImageUrl")
    private String perfumeImageUrl;

    @Column(name = "gender")
    private String gender;

    @ElementCollection
    @CollectionTable(name = "mainAccord", joinColumns = @JoinColumn(name = "perfumeId"))
    @Column(name = "accordName")
    private List<String> mainAccords;

    @ElementCollection
    @CollectionTable(name = "topNote", joinColumns = @JoinColumn(name = "perfumeId"))
    @Column(name = "noteName")
    private List<String> topNotes;

    @ElementCollection
    @CollectionTable(name = "middleNote", joinColumns = @JoinColumn(name = "perfumeId"))
    @Column(name = "noteName")
    private List<String> middleNotes;

    @ElementCollection
    @CollectionTable(name = "baseNote", joinColumns = @JoinColumn(name = "perfumeId"))
    @Column(name = "noteName")
    private List<String> baseNotes;

    @Builder
    public Perfume(String perfumeName, String perfumeBrand, String perfumeImageUrl, List<String> mainAccords,
                   List<String> topNotes, List<String> middleNotes, List<String> baseNotes, String gender) {
        this.perfumeName = perfumeName;
        this.perfumeBrand = perfumeBrand;
        this.perfumeImageUrl = perfumeImageUrl;
        this.mainAccords = mainAccords;
        this.topNotes = topNotes;
        this.middleNotes = middleNotes;
        this.baseNotes = baseNotes;
        this.gender= gender;
    }
}
