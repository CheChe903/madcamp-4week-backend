package com.example.madcamp_4week.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class AccordInfo {
    private Long id;
    private String accordName;
    private String accordKoreanName;
}
