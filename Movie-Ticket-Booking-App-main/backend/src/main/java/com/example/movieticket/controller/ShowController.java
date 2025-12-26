
package com.example.movieticket.controller;

import com.example.movieticket.dto.ShowResponse;
import com.example.movieticket.entity.Show;
import com.example.movieticket.repository.BookingRepository;
import com.example.movieticket.repository.ShowRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;

    public ShowController(ShowRepository showRepository, BookingRepository bookingRepository) {
        this.showRepository = showRepository;
        this.bookingRepository = bookingRepository;
    }

    // Get booked seats for a show
    @GetMapping("/{showId}/booked-seats")
    public List<String> getBookedSeats(@PathVariable Long showId) {
        return bookingRepository.findBookedSeatsByShowId(showId);
    }

    @GetMapping("/{showId}")
    public ShowResponse getShow(@PathVariable Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Booked seats lao from BookingRepository
        List<String> bookedSeats = bookingRepository.findBookedSeatsByShowId(showId);

        return new ShowResponse(
                show.getId(),
                show.getMovie().getTitle(),
                show.getTotalSeats(),
                bookedSeats,
                show.getShowTime(),
                show.getScreen());
    }
}
