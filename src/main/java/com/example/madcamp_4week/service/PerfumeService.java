package com.example.madcamp_4week.service;

import com.example.madcamp_4week.domain.Accord;
import com.example.madcamp_4week.domain.Mood;
import com.example.madcamp_4week.domain.Perfume;
import com.example.madcamp_4week.domain.dto.response.AccordInfo;
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
                .filter(perfume -> "unknown".equalsIgnoreCase(gender) || perfume.getGender().equalsIgnoreCase(gender))
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

        return RecommendPerfumeInfoList.builder()
                .recommendPerfumeInfoList(recommendedPerfumes)
                .build();
    }

    private RecommendPerfumeInfo convertToRecommendPerfumeInfo(Perfume perfume) {
        List<AccordInfo> mainAccords = perfume.getMainAccords().stream()
                .map(this::removePercentage)
                .map(accordName -> {
                    // Accord 이름으로 Accord를 찾되, 여러 결과를 반환
                    List<Accord> accords = accordRepository.findByAccordName(accordName);
                    if (accords.isEmpty()) {
                        log.warn("Accord not found for name: {}", accordName);
                        return AccordInfo.builder()
                                .accordName(accordName)
                                .build(); // 필수 필드만 설정
                    }

                    // 결과 중 첫 번째 항목만 선택
                    Accord accord = accords.get(0);
                    return AccordInfo.builder()
                            .id(accord.getId())
                            .accordName(accord.getAccordName())
                            .accordKoreanName(accord.getAccordKoreanName())
                            .accordImageUrl(accord.getAccordImageUrl())
                            .accordExplanation(accord.getAccordExplanation())
                            .build();
                })
                .distinct() // 중복 제거
                .limit(3) // 결과를 3개로 제한
                .collect(Collectors.toList());

        return RecommendPerfumeInfo.builder()
                .perfumeName(perfume.getPerfumeName())
                .perfumeImageUrl(perfume.getPerfumeImageUrl())
                .perfumeBrand(perfume.getPerfumeBrand())
                .mainAccords(mainAccords)
                .topNotes(perfume.getTopNotes())
                .middleNotes(perfume.getMiddleNotes())
                .baseNotes(perfume.getBaseNotes())
                .build();
    }

    private String removePercentage(String accordName) {
        // 퍼센트 값을 제거하여 순수한 accordName 추출
        int percentIndex = accordName.indexOf('(');
        return percentIndex == -1 ? accordName : accordName.substring(0, percentIndex).trim();
    }
    private Set<String> getLikedAccordsFromIds(List<Long> accordIds) {
        if (accordIds == null || accordIds.isEmpty()) {
            return Collections.emptySet();
        }
        return accordRepository.findAllById(accordIds).stream()
                .map(Accord::getAccordName) // 소문자 변환 제거
                .collect(Collectors.toSet());
    }

    private Set<String> getDislikedAccordsFromMoods(List<Long> moodIds) {
        if (moodIds == null || moodIds.isEmpty()) {
            return Collections.emptySet();
        }
        return moodRepository.findAllById(moodIds).stream()
                .flatMap(mood -> mood.getAccords().stream())
                .map(Accord::getAccordName) // 소문자 변환 제거
                .collect(Collectors.toSet());
    }

    private double calculateScore(Perfume perfume, Set<String> likedAccords, Set<String> dislikedAccords) {
        Set<String> mainAccords = new HashSet<>(perfume.getMainAccords());
        Set<String> allNotes = new HashSet<>();
        allNotes.addAll(perfume.getTopNotes());
        allNotes.addAll(perfume.getMiddleNotes());
        allNotes.addAll(perfume.getBaseNotes());

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
                .mainAccords(parseList(columns[5]).stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()))
                .topNotes(parseList(columns[6]).stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()))
                .middleNotes(parseList(columns[7]).stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()))
                .baseNotes(parseList(columns[8]).stream()
                        .map(String::toLowerCase)
                        .collect(Collectors.toList()))
                .build();
    }


    private List<String> parseList(String data) {
        return Arrays.stream(data.split(";"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
