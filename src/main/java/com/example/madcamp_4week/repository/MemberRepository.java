package com.example.madcamp_4week.repository;
import com.example.madcamp_4week.domain.Perfume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Perfume, Long> {
}