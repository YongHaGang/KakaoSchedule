package com.yongha.toy1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@ToString
public class KakaoAuthRequest {
    @JsonProperty("grant_type")
    private String grantType = "authorization_code";	//	authorization_code로 고정
    @JsonProperty("client_id")
    private String clientId = "";	//	앱 REST API 키
    @JsonProperty("redirect_uri")
    private String redirectUri = "";		//인가 코드가 리다이렉트된 URI
    @JsonProperty("code")
    private String code = "";            // 인가 코드 받기 요청으로 얻은 인가 코드
    @JsonProperty("client_secret")
    private String clientSecret = "EZlCIkqcg3hOsp9r5JonGG8s19WAvZEV"; // 토큰 발급 시, 보안을 강화하기 위해 추가 확인하는 코드

    public KakaoAuthRequest(String clientId, String redirectUri, String code) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.code = code;
    }


    public MultiValueMap<String, String> toMap() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("grant_type", grantType);
        multiValueMap.add("client_id", clientId);
        multiValueMap.add("redirect_uri", redirectUri);
        multiValueMap.add("code", code);
        multiValueMap.add("client_secret", clientSecret);
        return multiValueMap;
    }
}
