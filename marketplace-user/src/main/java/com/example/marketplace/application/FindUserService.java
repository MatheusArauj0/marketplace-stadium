package com.example.marketplace.application;

import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.mapper.UserMapper;
import com.example.marketplace.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse findById(UUID id) {
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity not found")));
    }

}
