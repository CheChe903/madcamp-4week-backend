package com.example.madcamp_4week.data;

import com.example.madcamp_4week.domain.Perfume;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvParser {

    public static List<Perfume> parseCsv(String filePath) throws IOException {
        List<Perfume> perfumes;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Read lines from the file without skipping the header line
            perfumes = br.lines()
                    .map(CsvParser::parseLineToPerfume)  // Parse each line to a Perfume object
                    .collect(Collectors.toList());  // Collect the results into a List
        }

        return perfumes;
    }

    private static Perfume parseLineToPerfume(String line) {
        String[] columns = line.split(",");
        return Perfume.builder()
                .perfumeName(columns[0].trim())
                .perfumeBrand(columns[1].trim())
                .perfumeImageUrl(columns[2].trim())
                .gender(columns[3].trim())
                .mainAccords(parseList(columns[4]))
                .topNotes(parseList(columns[5]))
                .middleNotes(parseList(columns[6]))
                .baseNotes(parseList(columns[7]))
                .build();
    }

    private static List<String> parseList(String data) {
        return Arrays.stream(data.split(";"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
