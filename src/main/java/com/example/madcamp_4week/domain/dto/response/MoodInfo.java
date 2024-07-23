package com.example.madcamp_4week.domain.dto.response;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class MoodInfo {

    private Long id;
    private String moodName;
    private String moodKoreanName;
    private String moodImageUrl;
    private String moodExplanation;
}
