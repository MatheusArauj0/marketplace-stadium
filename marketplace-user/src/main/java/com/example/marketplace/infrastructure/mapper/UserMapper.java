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
public interf