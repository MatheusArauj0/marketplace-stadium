package com.example.marketplace.application;

import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.mapper.UserMapper;
import com.example.marketplace.infrastructure.repository.UserRepository;
import com.example.marketplace.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        log.debug("Listando todos os usuários");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    public UserResponse findById(UUID id) {
        log.debug("Buscando usuário ID={}", id);
        return userMapper.toUserResponse(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Usuário", id)));
    }
}
