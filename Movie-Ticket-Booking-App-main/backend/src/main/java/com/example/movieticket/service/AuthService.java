package com.example.movieticket.service;

import com.example.movieticket.dto.AuthDtos.*;
import com.example.movieticket.entity.SessionToken;
import com.example.movieticket.entity.User;
import com.example.movieticket.repository.SessionTokenRepository;
import com.example.movieticket.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final SessionTokenRepository tokenRepo;

    public AuthService(UserRepository userRepo, SessionTokenRepository tokenRepo) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
    }

    public void register(RegisterRequest req) {
        userRepo.findByEmail(req.email()).ifPresent(u -> {
            throw new RuntimeException("Email already registered");
        });
        String hash = BCrypt.hashpw(req.password(), BCrypt.gensalt());
        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .passwordHash(hash)
                .build();
        userRepo.save(user);
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (!BCrypt.checkpw(req.password(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = UUID.randomUUID().toString();
        SessionToken st = SessionToken.builder().token(token).user(user).build();
        tokenRepo.save(st);
        return new LoginResponse(token, user.getId(), user.getName(), user.getEmail());
    }

    public User requireUser(String token) {
        return tokenRepo.findByToken(token).map(SessionToken::getUser)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
    }
}
