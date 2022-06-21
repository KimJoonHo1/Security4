package com.example.security2.controller;

import com.example.security2.auth.PrincipalDetails;
import com.example.security2.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService service;

    @GetMapping("/loginForm")
    public String loginForm(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "errorMessage", required = false) String errorMessage,
            @AuthenticationPrincipal PrincipalDetails userDetails,
            Model model
    ) {
        if(userDetails!= null) {
            if(userDetails.getUser() != null) {
                return "redirect:/";
            }
        }

        String errorMessageStr = null;

        if(error != null && errorMessage != null) {
            if(errorMessage.equals("er_01")) {
                errorMessageStr = "아이디 또는 비밀번호가 맞지 않습니다.";
            } else if(errorMessage.equals("er_02")) {
                errorMessageStr = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
            } else if(errorMessage.equals("er_03")) {
                errorMessageStr = "인증 요구가 거부되었습니다. 관리자에게 문의하세요.";
            } else if(errorMessage.equals("er_00")) {
                errorMessageStr = "알 수 없는 에러로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
            } else {
                errorMessageStr = "알 수 없는 에러로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
            }
            model.addAttribute("error", error);
            model.addAttribute("errorMessageStr", errorMessageStr);
        }
        return "/loginForm";
    }

    @GetMapping({"", "/"})
    public String index() {
        return "/index";
    }

    @GetMapping("/joinForm")
    public String joinForm(@AuthenticationPrincipal PrincipalDetails userDetails) {
        if(userDetails!= null) { {
            if(userDetails.getUser() != null) {
                return "redirect:/";
            }
        }

        }
        return "/joinForm";
    }

    @PostMapping("/join")
    public String join(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email
    ) {
        int result = service.join(username, password, email);

        if(result == 0) {
            return "redirect:/joinForm";
        }
        return "loginForm";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "user";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/night")
    @ResponseBody
    public String night() {
        return "night";
    }
}
