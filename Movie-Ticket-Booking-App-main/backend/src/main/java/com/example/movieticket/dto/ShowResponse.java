package com.example.movieticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ShowResponse {
    private Long id;
    private String movieTitle;
    private int totalSeats;
    private List<String> bookedSeats;
    private LocalDateTime showTime;
    private String screen;
}
