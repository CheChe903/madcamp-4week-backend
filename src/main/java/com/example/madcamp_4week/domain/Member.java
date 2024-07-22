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
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="memberId")
    private Long id;

    @Column(name= "userId")
    private String userId;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "member_perfume",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "perfume_id")
    )
    private List<Perfume> perfumes;
    @Builder
    public Member(String userId, String password, String name, List<Perfume> perfumes) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.perfumes = perfumes;
    }
}
