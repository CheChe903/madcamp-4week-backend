package com.example.madcamp_4week.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class RecommendPerfumeInfo {

    private String perfumeName;
    private String perfumeImageUrl;
    private String perfumeBrand;
    private List<AccordInfo> mainAccords;
    private List<String> topNotes;
    private List<String> middleNotes;
    private List<String> baseNotes;
}
