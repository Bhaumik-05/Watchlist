package com.movies.watchlist.dto;

import lombok.*;

@Data
public class ReviewDTO {
    private Long id;
    private String username;
    private String movieTitle;
    private Integer rating;
    private String comment;
}