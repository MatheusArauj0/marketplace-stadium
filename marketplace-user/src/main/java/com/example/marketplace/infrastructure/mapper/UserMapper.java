package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.User;
import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.entities.RoleEntity;
import com.example.marketplace.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        uses = {EmailMapper.class, DocumentMapper.class})
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStrings")
    UserResponse toUserResponse(UserEntity user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStrings")
    User toUser(UserEntity user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "stringsToRoles")
    UserEntity toUserEntity(User user);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToStrings")
    UserResponse toUserResponse(User user);

    @Named("rolesToStrings")
    default Set<String> rolesToStrings(Set<RoleEntity> roles) {
        if (roles == null) return Collections.emptySet();
        return roles.stream()
                .map(RoleEntity::getAuthority)
                .collect(Collectors.toSet());
    }

    @Named("stringsToRoles")
    default Set<RoleEntity> stringsToRoles(Set<String> roles) {
        if (roles == null) return Collections.emptySet();
        return roles.stream()
                .map(authority -> {
                    var role = new RoleEntity();
                    role.setAuthority(authority);
                    return role;
                })
                .collect(Collectors.toSet());
    }
}
