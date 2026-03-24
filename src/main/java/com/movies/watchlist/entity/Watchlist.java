package com.movies.watchlist.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "watchlist")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MANY watchlist entries belong to ONE user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // MANY watchlist entries refer to ONE movie
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    private String status; // WATCHED / PLANNED

    private LocalDate addedDate;
}