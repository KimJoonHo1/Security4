package com.example.security2.auth;

import com.example.security2.model.User;
import com.example.security2.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private SecurityRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        User userEntity = repository.login(username); // 로그인 시도
        System.out.println("user : " + userEntity);
        if(userEntity != null) {
            return new PrincipalDetails(userEntity); // 로그인 성공시 세션에 저장
        }
        return null;
    }
}
