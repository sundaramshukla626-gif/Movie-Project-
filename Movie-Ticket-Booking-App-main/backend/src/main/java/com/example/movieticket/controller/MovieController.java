package com.example.movieticket.controller;

import com.example.movieticket.entity.Movie;
import com.example.movieticket.entity.Show;
import com.example.movieticket.service.MovieService;
import com.example.movieticket.service.ShowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "false")
public class MovieController {
    private final MovieService movieService;
    private final ShowService showService;

    public MovieController(MovieService movieService, ShowService showService) {
        this.movieService = movieService;
        this.showService = showService;
    }

    @GetMapping("/movies")
    public List<Movie> movies() {
        return movieService.list();
    }

    @GetMapping("/movies/{id}/shows")
    public List<Show> shows(@PathVariable Long id) {
        return showService.byMovie(id);
    }
}
