package com.example.movieticket.config;

import com.example.movieticket.entity.Movie;
import com.example.movieticket.entity.Show;
import com.example.movieticket.repository.MovieRepository;
import com.example.movieticket.repository.ShowRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataLoader {
        @Bean
        CommandLineRunner load(MovieRepository movieRepo, ShowRepository showRepo) {
                return args -> {
                        if (movieRepo.count() == 0) {
                                Movie m1 = movieRepo.save(
                                                Movie.builder().title("URI").description("Real Story")
                                                                .durationMinutes(148).basePrice(new BigDecimal("200"))
                                                                .posterUrl("http://localhost:8080/images/Uri.jpg")
                                                                .build());
                                Movie m2 = movieRepo
                                                .save(Movie.builder().title("Saiyaara").description("Love story")
                                                                .durationMinutes(169).basePrice(new BigDecimal("200"))
                                                                .posterUrl("http://localhost:8080/images/Saiyaara.jpg")
                                                                .build());

                                Movie m3 = movieRepo
                                                .save(Movie.builder().title("Fighter").description("AirForce move")
                                                                .durationMinutes(166).basePrice(new BigDecimal("200"))
                                                                .posterUrl(
                                                                                "http://localhost:8080/images/Fighter.jpg")
                                                                .build());
                                Movie m4 = movieRepo.save(Movie.builder().title("War 2").description("Action")
                                                .durationMinutes(166).basePrice(new BigDecimal("200"))
                                                .posterUrl("http://localhost:8080/images/war2.jpg")
                                                .build());
                                Movie m5 = movieRepo.save(Movie.builder().title("Coolie").description("Action")
                                                .durationMinutes(166).basePrice(new BigDecimal("200"))
                                                .posterUrl("http://localhost:8080/images/Coolie.jpg")
                                                .build());
                                Movie m6 = movieRepo.save(Movie.builder().title("RRR").description("Action")
                                                .durationMinutes(166).basePrice(new BigDecimal("200"))
                                                .posterUrl("http://localhost:8080/images/RRR.jpg")
                                                .build());
                                Movie m7 = movieRepo.save(Movie.builder().title("Jaat").description("Action")
                                                .durationMinutes(165).basePrice(new BigDecimal("200"))
                                                .posterUrl("http://localhost:8080/images/Jaat_film_poster.jpg")
                                                .build());

                                showRepo.saveAll(List.of(
                                                Show.builder().movie(m1)
                                                                .showTime(LocalDateTime.now().plusDays(1).withHour(10)
                                                                                .withMinute(0))
                                                                .screen("Screen 1").totalSeats(100)
                                                                .build(),
                                                Show.builder().movie(m1)
                                                                .showTime(LocalDateTime.now().plusDays(1).withHour(18)
                                                                                .withMinute(0))
                                                                .screen("Screen 2").totalSeats(100)
                                                                .build(),
                                                Show.builder().movie(m2)
                                                                .showTime(LocalDateTime.now().plusDays(2).withHour(14)
                                                                                .withMinute(0))
                                                                .screen("Screen 1").totalSeats(120)
                                                                .build(),

                                                Show.builder().movie(m3)
                                                                .showTime(LocalDateTime.now().plusDays(3).withHour(20)
                                                                                .withMinute(0))
                                                                .screen("IMAX").totalSeats(150)
                                                                .build(),
                                                Show.builder().movie(m4)
                                                                .showTime(LocalDateTime.now().plusDays(3).withHour(20)
                                                                                .withMinute(0))
                                                                .screen("IMAX").totalSeats(150)
                                                                .build(),

                                                Show.builder().movie(m5)
                                                                .showTime(LocalDateTime.now().plusDays(3).withHour(20)
                                                                                .withMinute(0))
                                                                .screen("IMAX").totalSeats(150)
                                                                .build(),
                                                Show.builder().movie(m6)
                                                                .showTime(LocalDateTime.now().plusDays(2).withHour(20)
                                                                                .withMinute(0))
                                                                .screen("IMAX").totalSeats(150)
                                                                .build(),
                                                Show.builder().movie(m7)
                                                                .showTime(LocalDateTime.now().plusDays(2).withHour(20)
                                                                                .withMinute(0))
                                                                .screen("IMAX").totalSeats(150)
                                                                .build()));
                        }
                };
        }
}
