package com.itacademy.web.controller;

import com.itacademy.web.util.UtilText;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/"+ UtilText.HOME)
    public String showHomePage() {
        return UtilText.HOME;
    }
}
