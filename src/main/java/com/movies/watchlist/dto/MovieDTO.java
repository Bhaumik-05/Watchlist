package com.movies.watchlist.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private Integer releaseYear;
}