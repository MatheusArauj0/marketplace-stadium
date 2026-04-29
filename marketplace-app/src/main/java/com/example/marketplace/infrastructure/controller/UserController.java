package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.FindUserService;
import com.example.marketplace.infrastructure.dto.UserResponse;
import com.example.marketplace.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final FindUserService findUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(findUserService.findAll());
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(findUserService.findById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UUID userId = SecurityUtils.getAuthenticatedUserId();
        return ResponseEntity.ok(findUserService.findById(userId));
    }
}
