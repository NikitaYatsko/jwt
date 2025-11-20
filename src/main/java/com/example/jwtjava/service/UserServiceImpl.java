package com.example.jwtjava.service;

import com.example.jwtjava.mapper.UserMapper;
import com.example.jwtjava.model.dto.JwtAuthenticationDto;
import com.example.jwtjava.model.dto.RefreshTokenDto;
import com.example.jwtjava.model.dto.UserCredentialsDto;
import com.example.jwtjava.model.dto.UserDto;
import com.example.jwtjava.model.entity.User;
import com.example.jwtjava.repository.UserRepository;
import com.example.jwtjava.security.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }


    private User findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.getUserByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(),user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Email not found");
    }

    private User findByEmail(String email) throws Exception {
        return userRepository.getUserByEmail(email).orElseThrow(
                () -> new Exception("User not found")
        );
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new EntityNotFoundException(email));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(UUID id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        return userMapper.toDto(user);
    }
}
