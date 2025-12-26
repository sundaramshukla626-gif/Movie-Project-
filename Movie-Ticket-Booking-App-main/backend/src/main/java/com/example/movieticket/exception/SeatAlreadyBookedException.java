package com.example.movieticket.exception;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException(String seat) {
        super("Seat already booked: " + seat);
    }
}
