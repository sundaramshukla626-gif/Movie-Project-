package com.example.movieticket.controller;

import com.example.movieticket.dto.WalletDtos.*;
import com.example.movieticket.entity.User;
import com.example.movieticket.service.AuthService;
import com.example.movieticket.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
// @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "false")
public class WalletController {
    private final WalletService walletService;
    private final AuthService authService;

    public WalletController(WalletService walletService, AuthService authService) {
        this.walletService = walletService;
        this.authService = authService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<BalanceResponse> deposit(@RequestHeader("X-Auth-Token") String token,
            @RequestBody DepositRequest req) {
        User u = authService.requireUser(token);
        return ResponseEntity.ok(new BalanceResponse(walletService.deposit(u, req.amount())));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> balance(@RequestHeader("X-Auth-Token") String token) {
        User u = authService.requireUser(token);
        return ResponseEntity.ok(new BalanceResponse(walletService.balance(u)));
    }
}
