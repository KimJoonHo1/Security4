package com.example.security2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }
}
