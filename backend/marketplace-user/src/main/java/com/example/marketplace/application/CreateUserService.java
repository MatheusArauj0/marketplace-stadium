package com.example.marketplace.application;

import com.example.marketplace.domain.model.Document;
import com.example.marketplace.domain.model.Email;
import com.example.marketplace.domain.model.User;
import com.example.marketplace.infrastructure.dto.UserRequest;
import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.mapper.UserMapper;
import com.example.marketplace.infrastructure.repository.UserRepository;
import com.example.marketplace.infrastructure.repository.jpa.UserRepositoryJpa;
import com.example.marketplace.shared.exception.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepositoryJpa userRepositoryJpa;
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest userRequest) {
        log.info("Criando usuário com e-mail={}", userRequest.email());
        if (userRepository.existsByEmail(userRequest.email())) {
            log.warn("Tentativa de cadastro com e-mail duplicado: {}", userRequest.email());
            throw new DuplicateResourceException("Usuário", "email", userRequest.email());
        }

        var user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(new Email(userRequest.email()))
                .password(passwordEncoder.encode(userRequest.password()))
                .document(new Document(Document.of(userRequest.document()), userRequest.document()))
                .build();

        var saved = mapper.toUserResponse(userRepositoryJpa.createUser(user));
        log.info("Usuário criado com ID={}", saved.getId());
        return saved;
    }
}
