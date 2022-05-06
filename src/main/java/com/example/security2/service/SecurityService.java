package com.example.security2.service;

import com.example.security2.model.User;
import com.example.security2.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    @Autowired
    private SecurityRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public int join(String username, String password, String email) {
        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setRole("ROLE_USER");
        user.setProvider("Default");

        String bcPwd = bCryptPasswordEncoder.encode(password);
        user.setPassword(bcPwd);

        return repository.save(user);
    }
}
