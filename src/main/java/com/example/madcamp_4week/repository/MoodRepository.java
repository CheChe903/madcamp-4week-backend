package com.example.madcamp_4week.repository;

import com.example.madcamp_4week.domain.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MoodRepository extends JpaRepository<Mood, Long> {
}