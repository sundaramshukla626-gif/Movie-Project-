
package com.example.movieticket.controller;

import com.example.movieticket.dto.BookingRequest;
import com.example.movieticket.entity.Booking;
import com.example.movieticket.service.BookingService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public Booking bookTicket(@RequestBody BookingRequest request) {
        return bookingService.book(
                request.getUserId(),
                request.getShowId(),
                request.getSeats());
    }

    @GetMapping("/my/{userId}")
    public List<Booking> getMyBookings(@PathVariable Long userId) {
        return bookingService.listByUser(userId);
    }

    @DeleteMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId,
            @RequestParam Long userId) {
        bookingService.cancelBooking(bookingId, userId);
        return "Booking cancelled successfully!";
    }
}
