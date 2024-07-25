package com.example.madcamp_4week.domain.dto.request;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRecommendPerfumeRequest {
    private List<Long> likedAccordIds;
    private List<Long> dislikedMoodIds;
    private String gender;
}
