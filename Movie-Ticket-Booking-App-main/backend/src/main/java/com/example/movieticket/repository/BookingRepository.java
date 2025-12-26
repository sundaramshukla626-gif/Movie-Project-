package com.example.movieticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.Booking;
import com.example.movieticket.entity.User;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Show ke hisaab se saare seats nikalne ke liye
    @Query("SELECT s FROM Booking b JOIN b.seatNumbers s WHERE b.showRef.id = :showId")
    List<String> findBookedSeatsByShowId(@Param("showId") Long showId);

    List<Booking> findByUser(User user);

}
