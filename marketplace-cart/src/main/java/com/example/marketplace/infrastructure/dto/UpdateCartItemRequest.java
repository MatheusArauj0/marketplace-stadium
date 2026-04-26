package com.example.marketplace.infrastructure.dto;

public record UpdateCartItemRequest(Boolean selected,
                                    Integer quantity){}
