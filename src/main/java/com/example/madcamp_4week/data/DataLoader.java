package com.example.madcamp_4week.data;

import com.example.madcamp_4week.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PerfumeService perfumeService;

    @Override
    public void run(String... args) throws Exception {
        String csvFilePath = "/Users/mac/Desktop/study/madcamp/4week-item/perfumes_clean.csv"; // CSV 파일 경로를 설정하세요
        perfumeService.loadPerfumeData(csvFilePath);
    }
}