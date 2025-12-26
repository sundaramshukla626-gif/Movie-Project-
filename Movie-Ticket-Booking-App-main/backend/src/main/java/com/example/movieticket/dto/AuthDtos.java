package com.example.movieticket.dto;

public class AuthDtos {
    public record RegisterRequest(String name, String email, String password) {}
    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token, Long userId, String name, String email) {}
}
