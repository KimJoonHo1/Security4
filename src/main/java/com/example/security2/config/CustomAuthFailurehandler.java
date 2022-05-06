package com.example.security2.config;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthFailurehandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";
        if(exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "er_01";
        } else if(exception instanceof BadCredentialsException) {
            errorMessage = "er_01";
        } else if(exception instanceof DisabledException) {
            errorMessage = "er_02";
        } else if(exception instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "er_03";
        } else {
            errorMessage = "er_00";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");

        setDefaultFailureUrl("/loginForm?error=true&errorMessage="+errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
