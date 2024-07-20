package com.example.madcamp_4week.support.token;

import lombok.*;

/** 인증 토큰 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthToken {

    private String accessToken;
    private String refreshToken;
}
