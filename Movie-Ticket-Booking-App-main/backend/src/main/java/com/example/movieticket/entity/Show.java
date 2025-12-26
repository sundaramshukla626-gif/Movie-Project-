
package com.example.movieticket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // Relation to Movie
    @JoinColumn(name = "movie_id") // Foreign key in DB
    private Movie movie;

    private LocalDateTime showTime;
    private String screen;
    private int totalSeats;

    private double basePrice;
    @ElementCollection
    @Builder.Default
    @CollectionTable(name = "show_booked_seats", joinColumns = @JoinColumn(name = "show_id"))
    @Column(name = "seat_number")
    private List<String> bookedSeats = new ArrayList<>();

    @Transient
    public int getAvailableSeats() {
        return totalSeats - bookedSeats.size();
    }

}
