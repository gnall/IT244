package com.example.apis.items;

import jakarta.validation.constraints.NotBlank;

public record ItemUpdateRequest(@NotBlank String name, String value) {}

