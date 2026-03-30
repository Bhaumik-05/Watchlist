package com.movies.watchlist.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.movies.watchlist.entity.Movie;
import com.movies.watchlist.repository.*;

import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {

        // Check if a movie with the same title already exists
        Optional<Movie> existing = movieRepository.findByTitleIgnoreCase(movie.getTitle());
        if (existing.isPresent()) {
            throw new RuntimeException("Movie with title '" + movie.getTitle() + "' already exists");
        }

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public void deleteMovie(Long id) {
        // Delete all watchlist entries for this movie
        watchlistRepository.deleteAll(watchlistRepository.findByMovieId(id));

        // Delete all reviews for this movie
        reviewRepository.deleteAll(reviewRepository.findByMovieId(id));

        // Now safely delete the movie
        movieRepository.deleteById(id);
    }

    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}