package com.example.mural.dto;

public record ListedMessage(
        String from,
        String to,
        String message,
        String timestamp
) {}
