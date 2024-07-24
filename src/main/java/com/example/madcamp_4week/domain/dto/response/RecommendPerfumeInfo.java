package com.example.madcamp_4week.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class RecommendPerfumeInfo {

    String perfumeName;
    String perfumeImageUrl;
    String perfumeBrand;
    List<String> mainAccords;
    List<String> topNotes;
    List<String> middleNotes;
    List<String> baseNotes;
}
