package com.movies.watchlist.controller;

import com.movies.watchlist.entity.Watchlist;
import com.movies.watchlist.service.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    // Add to watchlist
    @PostMapping
    public Watchlist addToWatchlist(@RequestBody Watchlist watchlist) {
        return watchlistService.addToWatchlist(watchlist);
    }

    // Get watchlist by user
    @GetMapping("/user/{userId}")
    public List<Watchlist> getUserWatchlist(@PathVariable Long userId) {
        return watchlistService.getUserWatchlist(userId);
    }

    // Update watchlist (status change)
    @PutMapping
    public Watchlist updateWatchlist(@RequestBody Watchlist watchlist) {
        return watchlistService.updateWatchlist(watchlist);
    }

    // Delete from watchlist
    @DeleteMapping("/{id}")
    public String deleteWatchlist(@PathVariable Long id) {
        watchlistService.deleteFromWatchlist(id);
        return "Deleted successfully";
    }
}