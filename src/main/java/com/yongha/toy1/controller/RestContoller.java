package com.yongha.toy1.controller;

import com.yongha.toy1.service.KakaoAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDateTime;

@Controller
public class RestContoller {

    @Autowired
    KakaoAuthService kakaoAuthService;

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping(value = "/")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String index(Model model) {
        model.addAttribute("name", "yongha");
        return "index";
    }

    @GetMapping(value = "/login")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String login(@RequestParam("code") String authorizationCode) {
        logger.info("authorizationCode=" + authorizationCode);
        String accessCode = kakaoAuthService.getAccessToken(authorizationCode);
        logger.info("accessCode=" + accessCode);
        return "main";
    }

}