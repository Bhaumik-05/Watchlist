package com.movies.watchlist.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MANY reviews belong to ONE user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // MANY reviews belong to ONE movie
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private Integer rating; // e.g., 1–5

    private String comment;
}