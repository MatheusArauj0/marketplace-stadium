package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.User;
import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = { EmailMapper.class, DocumentMapper.class })
public interface UserMapper {

    UserResponse toUserResponse(UserEntity user);

    User toUser(UserEntity user);

    UserEntity toUserEntity(User user);

    UserResponse toUserResponse(User user);

}
