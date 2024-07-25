package com.example.madcamp_4week.controller;


import com.example.madcamp_4week.domain.dto.request.GetAccordRequest;
import com.example.madcamp_4week.domain.dto.response.AccordListInfo;
import com.example.madcamp_4week.domain.dto.response.MoodListInfo;
import com.example.madcamp_4week.service.AccordService;
import com.example.madcamp_4week.support.ApiResponse;
import com.example.madcamp_4week.support.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accord")
public class AccordController {


    @Autowired
    AccordService accordService;

    @PostMapping("/getaccord")
    public ApiResponse<ApiResponse.SuccessBody<AccordListInfo>> getAccordsByMoodList
            (@Valid @RequestBody GetAccordRequest data)
    {
        List<Integer> moodIds = data.getSelectedMoodList();

        AccordListInfo accordListInfo = accordService.getAccordsByMoodList(moodIds);

        return ApiResponseGenerator.success(accordListInfo, HttpStatus.OK);
    }
}
