package com.movies.watchlist.dto;

import lombok.*;

@Data
public class WatchlistDTO {
    private Long id;
    private String username;   // just the name, not the full User object
    private String movieTitle; // just the title, not the full Movie object
    private String status;
    private String addedDate;
}