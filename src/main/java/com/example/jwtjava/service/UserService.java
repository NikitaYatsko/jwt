package com.example.jwtjava.service;

import com.example.jwtjava.model.dto.UserDto;
public interface UserService {
    UserDto getUserByEmail(String email);
    UserDto getUserById(Long id);
}
