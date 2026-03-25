package com.movies.watchlist.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private String username;
    private String movieTitle;
    private Integer rating;
    private String comment;
}