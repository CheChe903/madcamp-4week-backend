package com.example.madcamp_4week.controller;

import com.example.madcamp_4week.domain.dto.response.MoodListInfo;
import com.example.madcamp_4week.service.MoodService;
import com.example.madcamp_4week.support.ApiResponse;
import com.example.madcamp_4week.support.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/mood")
public class MoodController {


    @Autowired
    private MoodService moodService;
    @GetMapping("/moodlist")
    public ApiResponse<ApiResponse.SuccessBody<MoodListInfo>> chooseMoodList
            (HttpServletRequest request)
    {
        MoodListInfo moodListInfo = moodService.getAllMoodList();

        return ApiResponseGenerator.success((MoodListInfo) moodListInfo, HttpStatus.OK);
    }
}
