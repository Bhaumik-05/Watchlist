package com.movies.watchlist.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@JsonPropertyOrder({"id", "title", "genre", "releaseYear", "averageRating"})
public class MovieDTO {
    private String title;
    private String genre;
    private Integer releaseYear;
    private Double averageRating;
}