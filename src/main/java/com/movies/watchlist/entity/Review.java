package com.movies.watchlist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many reviews belong to one user (One user can give many reviews)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne // Many reviews belong to one user
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Min(1) @Max(10) // Min rating should be 1 and should not exceed 10
    @Column(nullable = false)
    private Double rating;

    private String comment;
}