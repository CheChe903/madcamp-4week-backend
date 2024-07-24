package com.example.madcamp_4week.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class RecommendPerfumeInfoList {


    private List<RecommendPerfumeInfo> recommendPerfumeInfoList;
}
