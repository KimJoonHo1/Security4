package com.example.security2.repository;

import com.example.security2.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SecurityRepository {

    int save(@Param("user") User user);

    User login(@Param("username") String username);
}
