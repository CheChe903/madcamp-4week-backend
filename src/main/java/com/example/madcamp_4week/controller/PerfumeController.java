package com.example.madcamp_4week.controller;


import com.example.madcamp_4week.domain.Atmosphere;
import com.example.madcamp_4week.service.PerfumeService;
import com.example.madcamp_4week.support.ApiResponse;
import com.example.madcamp_4week.support.ApiResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/perfume")
public class PerfumeController {



    private final PerfumeService perfumeService;






}
