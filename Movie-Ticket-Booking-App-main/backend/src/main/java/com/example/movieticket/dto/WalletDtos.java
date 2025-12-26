package com.example.movieticket.dto;

import java.math.BigDecimal;

public class WalletDtos {
    public record DepositRequest(BigDecimal amount) {}
    public record BalanceResponse(BigDecimal balance) {}
}
