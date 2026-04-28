package com.example.marketplace.infrastructure.repository.jpa;

import com.example.marketplace.domain.model.User;
import com.example.marketplace.infrastructure.mapper.UserMapper;
import com.example.marketplace.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryJpa {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(User user) {
        var userEntity = userMapper.toUserEntity(user);
        var savedUserEntity = userRepository.save(userEntity);
        return userMapper.toUser(savedUserEntity);
    }
}
