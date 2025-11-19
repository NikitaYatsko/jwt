package com.example.jwtjava.service;

import com.example.jwtjava.mapper.UserMapper;
import com.example.jwtjava.model.dto.UserDto;
import com.example.jwtjava.model.entity.User;
import com.example.jwtjava.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(() -> new EntityNotFoundException(email));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.getUserById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        return userMapper.toDto(user);
    }
}
