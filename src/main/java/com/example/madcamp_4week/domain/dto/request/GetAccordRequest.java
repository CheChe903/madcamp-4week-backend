package com.example.madcamp_4week.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;



@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAccordRequest {

    List<Integer> selectedMoodList;
}
