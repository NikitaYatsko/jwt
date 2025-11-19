package com.example.jwtjava.security;

import com.example.jwtjava.model.dto.JwtAuthenticationDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    @Value("28ef5b834272a317b4b4b516a9f851aa")
    private String JwtSecret;

     public JwtAuthenticationDto generateAuthToken(String email){



     }
}
