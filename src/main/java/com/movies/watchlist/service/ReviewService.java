package com.movies.watchlist.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.movies.watchlist.entity.Review;
import com.movies.watchlist.entity.User;
import com.movies.watchlist.entity.Movie;
import com.movies.watchlist.repository.ReviewRepository;
import com.movies.watchlist.repository.UserRepository;
import com.movies.watchlist.repository.MovieRepository;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Review addReview(Review review) {

        Long userId = review.getUser().getId();
        Long movieId = review.getMovie().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        review.setUser(user);
        review.setMovie(movie);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByMovie(Long movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}