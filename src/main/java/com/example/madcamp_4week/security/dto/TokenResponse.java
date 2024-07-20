package com.example.madcamp_4week.security.dto;

import capston.busthecall.security.support.ResponseMessage;
import capston.busthecall.security.token.AuthToken;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class TokenResponse {

    private HttpStatus status;
    private ResponseMessage message;
    private AuthToken token;
}
