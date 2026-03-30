package com.movies.watchlist.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.movies.watchlist.entity.Movie;
import com.movies.watchlist.repository.*;
import com.movies.watchlist.dto.*;
import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    public Movie addMovie(Movie movie) {

        // Check if a movie with the same title already exists
        Optional<Movie> existing = movieRepository.findByTitleIgnoreCase(movie.getTitle());
        if (existing.isPresent()) {
            throw new RuntimeException("Movie with title '" + movie.getTitle() + "' already exists");
        }

        return movieRepository.save(movie);
    }

    public MovieDTO convertToDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setTitle(movie.getTitle());
        dto.setGenre(movie.getGenre());
        dto.setReleaseYear(movie.getReleaseYear());
        // Fetch average rating from reviews table for this movie
        Double avg = reviewRepository.findAverageRatingByMovieId(movie.getId());

        // If no reviews yet, set 0.0 instead of null
        dto.setAverageRating(avg != null ? avg : 0.0);
        return dto;
    }

    public MovieDTO getMovieByIdAsDTO(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
        return convertToDTO(movie);
    }

    public List<MovieDTO> getAllMoviesAsDTO() {
        return movieRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Autowired
    private WatchlistRepository watchlistRepository;

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