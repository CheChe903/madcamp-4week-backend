package com.example.madcamp_4week.security.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenName {

    ACCESS("access"), REFRESH("refresh");
    private final String name;
}
