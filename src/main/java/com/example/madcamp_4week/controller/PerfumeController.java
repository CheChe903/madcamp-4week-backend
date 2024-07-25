package com.example.madcamp_4week.controller;


import com.example.madcamp_4week.domain.Perfume;
import com.example.madcamp_4week.domain.dto.request.GetRecommendPerfumeRequest;
import com.example.madcamp_4week.domain.dto.response.RecommendPerfumeInfo;
import com.example.madcamp_4week.domain.dto.response.RecommendPerfumeInfoList;
import com.example.madcamp_4week.service.PerfumeService;
import com.example.madcamp_4week.support.ApiResponse;
import com.example.madcamp_4week.support.ApiResponseGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/perfume")
public class PerfumeController {


    private final PerfumeService perfumeService;

    @PostMapping("/recommend")
    public ApiResponse<ApiResponse.SuccessBody<RecommendPerfumeInfoList>> recommendPerfume
            (@Valid @RequestBody GetRecommendPerfumeRequest data)
    {

        List<Long> dislikedMoodList = data.getDislikedMoodIds();
        List<Long> likedAccordList = data.getLikedAccordIds();

        String gender = data.getGender();
        RecommendPerfumeInfoList res=  perfumeService.recommendPerfumes(likedAccordList,dislikedMoodList,gender);

        return ApiResponseGenerator.success(res, HttpStatus.CREATED);
    }

    @GetMapping("/perfumelist/{moodId}")
    public ApiResponse<ApiResponse.SuccessBody<RecommendPerfumeInfoList>> randomPerfumeList(@PathVariable Long moodId)
    {
        RecommendPerfumeInfoList  res= perfumeService.getRandomPerfumesByMood(moodId);
        return ApiResponseGenerator.success(res, HttpStatus.OK);
    }


}
