package com.example.madcamp_4week.controller;

import com.example.madcamp_4week.domain.Atmosphere;
import com.example.madcamp_4week.service.AtmosphereService;
import com.example.madcamp_4week.support.ApiResponse;
import com.example.madcamp_4week.support.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class AtmosphereController {


    @Autowired
    private AtmosphereService atmosphereService;
    @GetMapping("")
    public ApiResponse<ApiResponse.SuccessBody<List<Atmosphere>>> chooseAtmosphere
            (HttpServletRequest request)
    {
        List<Atmosphere> atmospheres = atmosphereService.getAllAtmospheres();

        return ApiResponseGenerator.success(atmospheres, HttpStatus.OK);
    }
}
