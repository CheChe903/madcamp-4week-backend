package com.example.madcamp_4week.service;

import com.example.madcamp_4week.domain.Accord;
import com.example.madcamp_4week.domain.dto.response.AccordInfo;
import com.example.madcamp_4week.domain.dto.response.AccordListInfo;
import com.example.madcamp_4week.repository.AccordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccordService {

    private final AccordRepository accordRepository;
    private static final int ACCORD_LIMIT = 7;


        @Transactional
    public AccordListInfo getAccordsByMoodList(List<Integer> moodIds) {
        List<Accord> allAccords = new ArrayList<>();

        for (Integer moodId : moodIds) {
            List<Accord> accords = accordRepository.findByMoodId(moodId);
            if (accords.size() > ACCORD_LIMIT) {
                accords = accords.subList(0, ACCORD_LIMIT);
            }
            allAccords.addAll(accords);
        }

        // DTO 변환
        List<AccordInfo> accordInfoList = allAccords.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return AccordListInfo.builder()
                .accordInfoList(accordInfoList)
                .build();
    }

    private AccordInfo convertToDto(Accord accord) {
        return AccordInfo.builder()
                .id(accord.getId())
                .accordName(accord.getAccordName())
                .accordKoreanName(accord.getAccordKoreanName())
                .accordImageUrl(accord.getAccordImageUrl())
                .accordExplanation(accord.getAccordExplanation())
                .moodId(accord.getMood().getId())
                .build();
    }
}
