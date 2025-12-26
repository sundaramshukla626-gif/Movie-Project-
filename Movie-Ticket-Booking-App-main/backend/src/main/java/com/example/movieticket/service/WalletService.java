package com.example.movieticket.service;

import com.example.movieticket.entity.User;
import com.example.movieticket.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {
    private final UserRepository userRepo;

    public WalletService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public BigDecimal deposit(User u, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Invalid amount");

        User user = userRepo.findById(u.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BigDecimal currentBalance = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;
        user.setBalance(currentBalance.add(amount));
        userRepo.save(user);
        return user.getBalance();
    }

    public BigDecimal balance(User user) {
        return user.getBalance();
    }
}
