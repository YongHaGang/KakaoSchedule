package com.yongha.toy1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoAuthResponse {
    @JsonProperty("grant_type")
    private String tokenType; //		토큰 타입, bearer로 고정	O
    @JsonProperty("access_token")
    private String accessToken; //		사용자 액세스 토큰 값	O
    @JsonProperty("expires_in")
    private Integer expiresIn;	//	액세스 토큰 만료 시간(초)	O
    @JsonProperty("refresh_token")
    private String refreshToken; //		사용자 리프레시 토큰 값	O
    @JsonProperty("refresh_token_expires_in")
    private Integer refreshTokenExpiresIn; //		리프레시 토큰 만료 시간(초)	O
}
