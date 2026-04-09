package com.example.mural.dto;

public record MessageDTO(
        long id,
        String from,
        String to,
        String message,
        String timestamp
) {}
