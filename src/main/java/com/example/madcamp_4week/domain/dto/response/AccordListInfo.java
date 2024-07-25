package com.example.madcamp_4week.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class AccordListInfo {

    private List<AccordInfo> accordInfoList;
}
