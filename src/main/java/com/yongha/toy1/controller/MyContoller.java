package com.yongha.toy1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Controller
public class MyContoller {

    Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @GetMapping(value = "/")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String index(Model model) {
        model.addAttribute("name", "yongha");
        return "index";
    }

    @GetMapping(value = "/main")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String main(@RequestParam("code") String authorizationCode) {
        logger.error("LocalDateTime.now() + " "main " + authorizationCode);
        return "main";
    }

}