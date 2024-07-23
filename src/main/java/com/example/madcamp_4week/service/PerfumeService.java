package com.example.madcamp_4week.service;

import com.example.madcamp_4week.data.CsvParser;
import com.example.madcamp_4week.domain.Atmosphere;
import com.example.madcamp_4week.domain.Perfume;
import com.example.madcamp_4week.repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Transactional
    public void loadPerfumeData(String filePath) throws IOException {
        // Check if the repository is empty
        if (perfumeRepository.count() > 0) {
            System.out.println("Data already loaded, skipping...");
            return;
        }

        List<Perfume> perfumes;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            perfumes = br.lines()
                    .map(this::parseLineToPerfume)
                    .collect(Collectors.toList());
        }
        perfumeRepository.saveAll(perfumes);
    }

    private Perfume parseLineToPerfume(String line) {
        String[] columns = line.split(",");
        return Perfume.builder()
                .perfumeName(columns[1].trim())
                .perfumeBrand(columns[2].trim())
                .perfumeImageUrl(columns[3].trim())
                .gender(columns[4].trim())
                .mainAccords(parseList(columns[5]))
                .topNotes(parseList(columns[6]))
                .middleNotes(parseList(columns[7]))
                .baseNotes(parseList(columns[8]))
                .build();
    }

    private List<String> parseList(String data) {
        return Arrays.stream(data.split(";"))
                .map(String::trim)
                .collect(Collectors.toList());
    }

}