package com.example.apis.items;

import jakarta.validation.constraints.NotBlank;

public record ItemCreateRequest(@NotBlank String name, String value) {}

