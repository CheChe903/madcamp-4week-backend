package com.example.madcamp_4week.service;

import com.example.madcamp_4week.domain.Mood;
import com.example.madcamp_4week.domain.dto.response.MoodInfo;
import com.example.madcamp_4week.domain.dto.response.MoodListInfo;
import com.example.madcamp_4week.repository.MoodRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoodService {

    private final MoodRepository moodRepository;


    @Transactional
    public MoodListInfo getAllMoodList() {
        List<Mood> moods = moodRepository.findAll();
        List<MoodInfo> moodInfoList = moods.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return MoodListInfo.builder()
                .moodInfoList(moodInfoList)
                .build();
    }

    private MoodInfo convertToDto(Mood mood) {

        return MoodInfo.builder()
                .id(mood.getId())
                .moodName(mood.getMoodName())
                .moodKoreanName(mood.getMoodKoreanName())
                .moodImageUrl(mood.getMoodImageUrl())
                .moodExplanation(mood.getMoodExplanation())
                .build();
    }

}
