package com.example.madcamp_4week.service;

import com.example.madcamp_4week.domain.Accord;
import com.example.madcamp_4week.domain.Mood;
import com.example.madcamp_4week.domain.Perfume;
import com.example.madcamp_4week.domain.dto.response.RecommendPerfumeInfo;
import com.example.madcamp_4week.domain.dto.response.RecommendPerfumeInfoList;
import com.example.madcamp_4week.repository.AccordRepository;
import com.example.madcamp_4week.repository.MoodRepository;
import com.example.madcamp_4week.repository.PerfumeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;



@Service
@Slf4j
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Autowired
    private MoodRepository moodRepository;

    @Autowired
    private AccordRepository accordRepository;

    @Transactional
    public void loadPerfumeData(String filePath) throws IOException {
        // Check if the repository is empty
        if (perfumeRepository.count() > 0) {
            System.out.println("Data already loaded, skipping...");
            return;
        }

        List<Perfume> perfumes;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            perfumes = br.lines()
                    .map(this::parseLineToPerfume)
                    .collect(Collectors.toList());
        }
        perfumeRepository.saveAll(perfumes);
    }

    public RecommendPerfumeInfoList recommendPerfumes(List<Long> likedAccordIds, List<Long> dislikedMoodIds, String gender) {
        List<Perfume> allPerfumes = perfumeRepository.findAll();
        Set<String> likedAccords = getLikedAccordsFromIds(likedAccordIds);
        Set<String> dislikedAccords = getDislikedAccordsFromMoods(dislikedMoodIds);


        List<RecommendPerfumeInfo> recommendedPerfumes = allPerfumes.stream()
                .filter(perfume -> {
                    return "unknown".equalsIgnoreCase(gender) || perfume.getGender().equalsIgnoreCase(gender);
                })
                .filter(perfume -> {
                    double score = calculateScore(perfume, likedAccords, dislikedAccords);
                    return score > 0;
                })
                .sorted((p1, p2) -> Double.compare(
                        calculateScore(p2, likedAccords, dislikedAccords),
                        calculateScore(p1, likedAccords, dislikedAccords)
                ))
                .limit(3)
                .map(this::convertToRecommendPerfumeInfo)
                .collect(Collectors.toList());

        System.out.println("추천된 향수 개수: " + recommendedPerfumes.size());

        return RecommendPerfumeInfoList.builder()
                .recommendPerfumeInfoList(recommendedPerfumes)
                .build();
    }

    private RecommendPerfumeInfo convertToRecommendPerfumeInfo(Perfume perfume) {
        return RecommendPerfumeInfo.builder()
                .perfumeName(perfume.getPerfumeName())
                .perfumeImageUrl(perfume.getPerfumeImageUrl())
                .perfumeBrand(perfume.getPerfumeBrand())
                .mainAccords(perfume.getMainAccords())
                .topNotes(perfume.getTopNotes())
                .middleNotes(perfume.getMiddleNotes())
                .baseNotes(perfume.getBaseNotes())
                .build();
    }

    private Set<String> getLikedAccordsFromIds(List<Long> accordIds) {
        if (accordIds == null || accordIds.isEmpty()) {
            return Collections.emptySet();
        }
        return accordRepository.findAllById(accordIds).stream()
                .map(Accord::getAccordName)
                .map(String::toLowerCase) // 소문자로 변환
                .collect(Collectors.toSet());
    }

    private Set<String> getDislikedAccordsFromMoods(List<Long> moodIds) {
        if (moodIds == null || moodIds.isEmpty()) {
            return Collections.emptySet();
        }
        return moodRepository.findAllById(moodIds).stream()
                .flatMap(mood -> mood.getAccords().stream())
                .map(Accord::getAccordName)
                .map(String::toLowerCase) // 소문자로 변환
                .collect(Collectors.toSet());
    }

    private double calculateScore(Perfume perfume, Set<String> likedAccords, Set<String> dislikedAccords) {
        Set<String> mainAccords = perfume.getMainAccords().stream()
                .map(String::toLowerCase) // 소문자로 변환
                .collect(Collectors.toSet());
        Set<String> allNotes = new HashSet<>();
        allNotes.addAll(perfume.getTopNotes().stream().map(String::toLowerCase).collect(Collectors.toSet())); // 소문자로 변환
        allNotes.addAll(perfume.getMiddleNotes().stream().map(String::toLowerCase).collect(Collectors.toSet())); // 소문자로 변환
        allNotes.addAll(perfume.getBaseNotes().stream().map(String::toLowerCase).collect(Collectors.toSet())); // 소문자로 변환

        double likeScore = likedAccords.stream()
                .filter(accord -> mainAccords.contains(accord) || allNotes.contains(accord))
                .count();

        double dislikeScore = dislikedAccords.stream()
                .filter(accord -> mainAccords.contains(accord) || allNotes.contains(accord))
                .count() * 1.5; // 가중치 1.5 적용

        return likeScore - dislikeScore;
    }

    private Perfume parseLineToPerfume(String line) {
        String[] columns = line.split(",");
        return Perfume.builder()
                .perfumeName(columns[1].trim())
                .perfumeBrand(columns[2].trim())
                .perfumeImageUrl(columns[3].trim())
                .gender(columns[4].trim())
                .mainAccords(parseList(columns[5]))
                .topNotes(parseList(columns[6]))
                .middleNotes(parseList(columns[7]))
                .baseNotes(parseList(columns[8]))
                .build();
    }

    private List<String> parseList(String data) {
        return Arrays.stream(data.split(";"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

