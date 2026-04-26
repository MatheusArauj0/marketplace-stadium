package com.example.marketplace.infrastructure.controller;

import com.example.marketplace.application.CreateUserService;
import com.example.marketplace.application.FindUserService;
import com.example.marketplace.infrastructure.dto.UserRequest;
import com.example.marketplace.infrastructure.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserControler {

    private final FindUserService findUserService;
    private final CreateUserService createUserService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllProducts() {
        return
                ResponseEntity.ok(findUserService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getProductById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(findUserService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(createUserService.createUser(request));
    }
}
