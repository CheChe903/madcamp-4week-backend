package com.example.madcamp_4week.repository;

import com.example.madcamp_4week.domain.Accord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccordRepository extends JpaRepository<Accord, Long> {

    @Query("SELECT a FROM Accord a WHERE a.mood.id = :moodId")
    List<Accord> findByMoodId(@Param("moodId") Integer moodId);

    @Query("SELECT a FROM Accord a WHERE a.accordName = :accordName")
    List<Accord> findByAccordName(@Param("accordName") String accordName);


}
