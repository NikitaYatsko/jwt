package com.example.jwtjava.service;

import com.example.jwtjava.model.dto.JwtAuthenticationDto;
import com.example.jwtjava.model.dto.RefreshTokenDto;
import com.example.jwtjava.model.dto.UserCredentialsDto;
import com.example.jwtjava.model.dto.UserDto;

import javax.naming.AuthenticationException;
import java.util.UUID;

public interface UserService {
    JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    UserDto createUser(UserDto userDto);
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

    UserDto getUserByEmail(String email);

    UserDto getUserById(UUID id);
}
