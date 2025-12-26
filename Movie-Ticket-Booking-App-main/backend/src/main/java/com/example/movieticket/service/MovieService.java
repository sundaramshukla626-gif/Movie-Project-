package com.example.movieticket.service;

import com.example.movieticket.entity.Movie;
import com.example.movieticket.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repo;

    public MovieService(MovieRepository repo) { this.repo = repo; }

    public List<Movie> list() { return repo.findAll(); }

    public Movie get(Long id) { return repo.findById(id).orElseThrow(); }
}
