package com.example.security2.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@NoArgsConstructor // 파라미터가 없는 기본 생성자 자동으로 생성
public class User {
    private String username;
    private String role;
    private String password;
    private String email;
    private Timestamp createDate;
    private String provider;
    private String providerId;

    @Builder
    public User(String username, String role, String password, String email, Timestamp createDate, String provider, String providerId) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.provider = provider;
        this.providerId = providerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
