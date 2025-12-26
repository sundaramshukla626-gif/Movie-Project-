package com.example.movieticket.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Long userId;
    private Long showId;
    private List<String> seats;
}
