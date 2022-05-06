package com.example.security2.config;

import com.example.security2.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private CustomAuthFailurehandler customAuthFailurehandler;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                // 로그인한 사용자만 접근 가능
                .antMatchers("/user/**").authenticated()
                // ROLE_ADMIN나 ROLE_MANAGER 권한이 있어야만 접근 가능
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                // ROLE_ADMIN 권한이 있어야만 접근 가능
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 그 외의 경로는 누구나 접근 가능
                .and()
                .formLogin()
                .loginPage("/loginForm") // 권한이 없는 페이지에 접근시 /loginForm으로 이동
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 로그인을 대신 진행함.
                .failureHandler(customAuthFailurehandler)
                .defaultSuccessUrl("/") // 로그인 성공 후 이동할 주소
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
    }
}
