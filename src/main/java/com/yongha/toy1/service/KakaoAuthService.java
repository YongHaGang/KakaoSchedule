package com.yongha.toy1.service;

import com.yongha.toy1.dto.KakaoAuthRequest;
import com.yongha.toy1.dto.KakaoAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@Service
public class KakaoAuthService{
    private String kakaoTokenUri = "https://kauth.kakao.com/oauth/token";
    @Value("${com.yongha.toy1.kakaoauthkey}")
    private String clientId;
    @Value("http://${com.yongha.toy1.domain}/login")
    private String redirectUri;

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private static RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    public KakaoAuthService() {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_FORM_URLENCODED
        ));
//        restTemplate.setMessageConverters(Arrays.asList(mappingJackson2HttpMessageConverter, new FormHttpMessageConverter()));
    }

    public String getAccessToken(String authCode) {
        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
//        requestParam.add("grant_type", "authorization_code");
//        requestParam.add("client_id", "ffcc64479c802de62008cd1ff61eb41c");
//        requestParam.add("redirect_uri", "http://192.168.35.119:8080/login");
//        requestParam.add("code", authCode);
//        requestParam.add("client_secret", "EZlCIkqcg3hOsp9r5JonGG8s19WAvZEV");
//        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(requestParam, headers);
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(new KakaoAuthRequest(clientId, redirectUri, authCode).toMap(), headers);
        logger.info(requestHttpEntity.toString());
        HttpEntity<KakaoAuthResponse> responseHttpEntity = restTemplate.exchange(kakaoTokenUri,
                HttpMethod.POST, requestHttpEntity, KakaoAuthResponse.class);
        KakaoAuthResponse kakaoAuthResponse = responseHttpEntity.getBody();
        logger.info(kakaoAuthResponse.toString());
        return kakaoAuthResponse.getAccessToken();
    }
}
