package com.movies.watchlist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String genre;

    @Min(value = 1888, message = "Movies didn't exist before 1888")
    @Max(value = 2100, message = "Invalid release year")
    private Integer releaseYear;
}