package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    default Email map(String value) {
        return new Email(value);
    }

    default String map(Email email) {
        return email.value();
    }
}
