package com.yongha.toy1.controller;

import com.yongha.toy1.dto.AccountInfo;
import com.yongha.toy1.service.KakaoAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RestController {

    @Autowired
    KakaoAuthService kakaoAuthService;

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("http://${com.yongha.toy1.domain}")
    private String domain;

    @GetMapping(value = "/")
    public ModelAndView root(ModelAndView modelAndView) {
        modelAndView.setView(new RedirectView("index"));
        return modelAndView;
    }

    @GetMapping(value = "/index")
    public ModelAndView index(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("index");
        modelAndView.addObject("kakao_javascriptkey", KakaoAuthService.javascriptKey);
        return modelAndView;
    }

    @GetMapping(value = "/make")
    public ModelAndView make(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams) {
        modelAndView.setViewName("make");
        modelAndView.addAllObjects(requestParams);
        return modelAndView;
    }

    @GetMapping(value = "/select")
    public ModelAndView select(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams) {
        modelAndView.setViewName("select");
        modelAndView.addAllObjects(requestParams);
        return modelAndView;
    }

    @GetMapping(value = "/result")
    public ModelAndView result(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams) {
        modelAndView.setViewName("result");
        modelAndView.addAllObjects(requestParams);
        return modelAndView;
    }

    @GetMapping(value = "/login/kakao")
    public ModelAndView login(HttpServletRequest httpServletRequest, @RequestParam Map<String, String> requestParams) {
        String code = requestParams.get("code");
        String state = requestParams.get("state");
        String accessCode = kakaoAuthService.getAccessToken(code, domain + httpServletRequest.getRequestURI());
        AccountInfo accountInfo = kakaoAuthService.getAccountInfo(accessCode);
        logger.info("state=" + state + " accountInfo=" + accountInfo);

        String[] stateArray = requestParams.get("state").split("/");
        String page = stateArray[0];
        int scheduleNumber = Integer.MIN_VALUE;
        if (stateArray.length >= 2) {
            try {
                scheduleNumber = Integer.parseInt(stateArray[1]);
            } catch (NumberFormatException e) {
                logger.error("state scheduleNumber is invalid", e);
            }
        }

        Map<String, Object> attributeMap = new HashMap();
        attributeMap.put("name", accountInfo.getName());
        attributeMap.put("thumbnailUri", accountInfo.getThumbnailUri());
        attributeMap.put("scheduleNumber", scheduleNumber);

        ModelAndView modelAndView = new ModelAndView();
        RedirectView redirectView = new RedirectView("main");
        switch (page) {
            case "make":
                redirectView.setUrl("/make");
                break;
            case "select":
                redirectView.setUrl("/select");
                break;
            case "result":
                redirectView.setUrl("/result");
                break;
            default:
                break;
        }
        redirectView.setAttributesMap(attributeMap);
        modelAndView.setView(redirectView);
        return modelAndView;
    }

}