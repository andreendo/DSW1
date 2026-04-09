package com.example.mural.dto;

import jakarta.validation.constraints.NotBlank;

public class MessageFormDTO {
    private Long id;
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotBlank
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("SendMessageForm[id=%d, from='%s', to='%s', message='%s']", id, from, to, message);
    }
}
