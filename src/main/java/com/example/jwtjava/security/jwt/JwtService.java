package com.example.jwtjava.security.jwt;

import com.example.jwtjava.model.dto.JwtAuthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {
    @Value("a8ce8bfe4d997ecf07be6064e5f776c3b87db543e663218c8ba4f520c2e03dee6819ea092f4f4fb8d96136de81a8da280e8e3298d92708080b8af7a8e82e452f")
    private String JwtSecret;
    private static final Logger LOG = LoggerFactory.getLogger(JwtService.class);


    public JwtAuthenticationDto generateAuthToken(String email) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateToken(email));
        jwtDto.setRefreshToken(generateRefreshToken(email));
        return jwtDto;

    }

    public JwtAuthenticationDto refreshBaseToken(String email, String refreshToken) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateToken(email));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            LOG.error("Expired JwtException", e);
        } catch (UnsupportedJwtException e) {
            LOG.error("Unsupported JwtException", e);
        } catch (MalformedJwtException e) {
            LOG.error("Malformed JwtException", e);
        } catch (SecurityException e) {
            LOG.error("Security Exception", e);
        } catch (Exception e) {
            LOG.error("Error", e);
        }
        return false;
    }

    private String generateRefreshToken(String email) {
        Date date = Date.from(LocalDateTime.now()
                .plusDays(1)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private String generateToken(String email) {
        Date date = Date.from(LocalDateTime.now()
                .plusMinutes(1)
                .atZone(ZoneId.systemDefault())
                .toInstant());

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
