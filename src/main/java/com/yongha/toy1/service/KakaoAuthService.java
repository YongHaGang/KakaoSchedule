package com.yongha.toy1.service;

import com.yongha.toy1.dto.AccountInfo;
import com.yongha.toy1.dto.KakaoAccountInfo;
import com.yongha.toy1.dto.KakaoAuthRequest;
import com.yongha.toy1.dto.KakaoAuthResponse;
import com.yongha.toy1.entity.Account;
import com.yongha.toy1.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class KakaoAuthService{
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    public static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    public static String javascriptKey;
    public static String restKey;
    public static String clientSecret;

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    AccountRepository accountRepository;

    private static RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    public KakaoAuthService(@Value("${com.yongha.toy1.kakao_javascriptkey}") String javascriptKey,
                            @Value("${com.yongha.toy1.kakao_restkey}") String restKey,
                            @Value("${com.yongha.toy1.kakao_clientsecret}") String clientSecret) {
        this.javascriptKey = javascriptKey;
        this.restKey = restKey;
        this.clientSecret = clientSecret;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_FORM_URLENCODED
        ));
    }

    public String getAccessToken(String authCode, String requestUri) {
        HttpEntity<MultiValueMap<String, String>> requestHttpEntity = new HttpEntity<>(new KakaoAuthRequest(restKey, requestUri, authCode, clientSecret).toMap(), headers);
        logger.info(requestHttpEntity.toString());
        HttpEntity<KakaoAuthResponse> responseHttpEntity = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, requestHttpEntity, KakaoAuthResponse.class);
        KakaoAuthResponse kakaoAuthResponse = responseHttpEntity.getBody();
        logger.info(kakaoAuthResponse.toString());
        return kakaoAuthResponse.getAccessToken();
    }

    public AccountInfo getAccountInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(AUTHORIZATION, wrapBearer(accessToken));
        String result = "";
        HttpEntity<Object> requestHttpEntity = new HttpEntity<>(headers);
        HttpEntity<KakaoAccountInfo> responseHttpEntity = restTemplate.exchange(USER_INFO_URI, HttpMethod.GET, requestHttpEntity, KakaoAccountInfo.class);
        Account account = new Account(responseHttpEntity.getBody());
        accountRepository.save(account);

        return responseHttpEntity.getBody();
    }

    private String wrapBearer(String str) {
        return "Bearer {" + str + "}";
    }
}
