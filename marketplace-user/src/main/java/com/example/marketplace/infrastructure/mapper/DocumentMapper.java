package com.example.marketplace.infrastructure.mapper;

import com.example.marketplace.domain.model.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    default Document map(String value) {
        return new Document(Document.of(value), value);
    }

    default String map(Document document) {
        return document.value();
    }
}
