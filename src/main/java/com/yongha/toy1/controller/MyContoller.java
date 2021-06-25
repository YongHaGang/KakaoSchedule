package com.yongha.toy1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MyContoller {

    @GetMapping(value = "/")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String index(Model model) {
        model.addAttribute("name", "yongha");
        return "index";
    }

    @GetMapping(value = "/main")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String main() {
        return "main";
    }

}
