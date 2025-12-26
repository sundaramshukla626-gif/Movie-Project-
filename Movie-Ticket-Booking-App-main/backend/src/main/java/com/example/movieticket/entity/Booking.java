package com.example.movieticket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show showRef;

    private int seatsBooked;

    @ElementCollection
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_number")
    @Builder.Default
    private List<String> seatNumbers = new ArrayList<>(); // e.g. ["S1","S2","S3"]

    private BigDecimal totalPrice;

    private BigDecimal basePrice; // ADD THIS FIELD

    @Builder.Default
    private LocalDateTime bookingTime = LocalDateTime.now();
}
