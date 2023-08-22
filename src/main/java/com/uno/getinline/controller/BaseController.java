package com.uno.getinline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class BaseController {

    @GetMapping("/")
    public String root() {
        return "index";
    }
}