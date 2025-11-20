package com.example.jwtjava.controller;

import com.example.jwtjava.model.dto.JwtAuthenticationDto;
import com.example.jwtjava.model.dto.RefreshTokenDto;
import com.example.jwtjava.model.dto.UserCredentialsDto;
import com.example.jwtjava.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody UserCredentialsDto userCredentialsDto) {
        try {
            System.out.println(userCredentialsDto);
            JwtAuthenticationDto dto = userService.signIn(userCredentialsDto);
            return ResponseEntity.ok(dto);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed");
        }

    }

    @PostMapping("/refresh")
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        return userService.refreshToken(refreshTokenDto);
    }
}
