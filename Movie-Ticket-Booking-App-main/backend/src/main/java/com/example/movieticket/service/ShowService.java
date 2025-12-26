package com.example.movieticket.service;

import com.example.movieticket.entity.Show;
import com.example.movieticket.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private final ShowRepository repo;

    public ShowService(ShowRepository repo) { this.repo = repo; }

    public List<Show> byMovie(Long movieId) { return repo.findByMovieId(movieId); }
    public Show get(Long id) { return repo.findById(id).orElseThrow(); }
}
