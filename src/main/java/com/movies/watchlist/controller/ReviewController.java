package com.movies.watchlist.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.movies.watchlist.entity.Review;
import com.movies.watchlist.service.ReviewService;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public Review addReview(@Valid @RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @GetMapping("/movie/{movieId}")
    public List<Review> getReviewsByMovie(@PathVariable Long movieId) {
        return reviewService.getReviewsByMovie(movieId);
    }

    @DeleteMapping("/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Review deleted successfully";
    }
}