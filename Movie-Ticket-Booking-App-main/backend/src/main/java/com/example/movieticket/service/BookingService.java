package com.example.movieticket.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.movieticket.entity.Booking;
import com.example.movieticket.entity.Show;
import com.example.movieticket.entity.User;
import com.example.movieticket.exception.InsufficientBalanceException;
import com.example.movieticket.exception.SeatAlreadyBookedException;
import com.example.movieticket.repository.BookingRepository;
import com.example.movieticket.repository.ShowRepository;
import com.example.movieticket.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final ShowRepository showRepo;
    private final UserRepository userRepo;

    public BookingService(BookingRepository bookingRepo, ShowRepository showRepo, UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.showRepo = showRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public Booking book(Long userId, Long showId, List<String> seats) {
        Show show = showRepo.findByIdForUpdate(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        User user = userRepo.findByIdForUpdate(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> alreadyBookedSeats = bookingRepo.findBookedSeatsByShowId(show.getId());

        for (String seat : seats) {
            if (alreadyBookedSeats.contains(seat)) {
                throw new SeatAlreadyBookedException(seat);
            }
        }

        BigDecimal totalPrice = show.getMovie().getBasePrice()
                .multiply(BigDecimal.valueOf(seats.size()));

        if (user.getBalance().compareTo(totalPrice) < 0) {
            throw new InsufficientBalanceException();
        }

        user.setBalance(user.getBalance().subtract(totalPrice));
        show.getBookedSeats().addAll(seats);

        userRepo.save(user);
        showRepo.save(show);

        Booking booking = Booking.builder()
                .user(user)
                .showRef(show)
                .seatsBooked(seats.size())
                .seatNumbers(seats)
                .totalPrice(totalPrice)
                .build();

        return bookingRepo.save(booking);
    }

    public List<Booking> listByUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return bookingRepo.findByUser(user);
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can cancel only your own bookings");
        }

        LocalDateTime showTime = booking.getShowRef().getShowTime();
        if (showTime.isBefore(LocalDateTime.now().plusHours(3))) {
            throw new RuntimeException("Cannot cancel within 3 hours of showtime");
        }

        // Refund money
        user.setBalance(user.getBalance().add(booking.getTotalPrice()));

        // Free booked seats
        booking.getShowRef().getBookedSeats().removeAll(booking.getSeatNumbers());

        userRepo.save(user);
        showRepo.save(booking.getShowRef());

        bookingRepo.delete(booking);
    }
}
