package com.example.marketplace.infrastructure.dto;

public record UserRequest(String firstName, String lastName, String email, String password, String document) {
}
