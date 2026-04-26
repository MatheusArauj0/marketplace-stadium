package com.example.marketplace.application;

import com.example.marketplace.domain.model.Document;
import com.example.marketplace.domain.model.Email;
import com.example.marketplace.domain.model.User;
import com.example.marketplace.infrastructure.dto.UserRequest;
import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.mapper.UserMapper;
import com.example.marketplace.infrastructure.repository.jpa.UserRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepositoryJpa userRepositoryJpa;
    private final UserMapper mapper;


    public UserResponse createUser(UserRequest userRequest) {
        var user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .email(new Email(userRequest.email()))
                .password(userRequest.password())
                .document(new Document(Document.of(userRequest.document()), userRequest.document()))
                .build();
        return mapper.toUserResponse(userRepositoryJpa.createUser(user));
    }
}
