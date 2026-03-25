package com.movies.watchlist.dto;

import lombok.*;

@Data
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private Integer releaseYear;
}