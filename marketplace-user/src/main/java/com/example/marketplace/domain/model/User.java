package com.example.marketplace.domain.model;

import com.example.marketplace.infrastructure.entities.RoleEntity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Getter
@Builder
public class User {

    private UUID id;
    private String firstName;
    private String lastName;
    private Email email;
    private String password;
    private Document document;
    private Set<RoleEntity> roles = new HashSet<>();
}
