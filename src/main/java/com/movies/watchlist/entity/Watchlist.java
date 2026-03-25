package com.movies.watchlist.entity;

import com.movies.watchlist.enums.WatchStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "watchlist")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Many Watchlist entries belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne // Many Watchlist entries refer to one movie
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WatchStatus status;

    private LocalDate addedDate;
}