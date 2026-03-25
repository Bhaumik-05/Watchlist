package com.movies.watchlist.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.movies.watchlist.entity.Watchlist;
import com.movies.watchlist.enums.WatchStatus;
import com.movies.watchlist.service.WatchlistService;
import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @PostMapping
    public Watchlist addToWatchlist(@RequestBody Watchlist watchlist) {
        return watchlistService.addToWatchlist(watchlist);
    }

    @GetMapping("/user/{userId}")
    public List<Watchlist> getUserWatchlist(@PathVariable Long userId) {
        return watchlistService.getUserWatchlist(userId);
    }

    @PutMapping("/{id}")
    public Watchlist updateWatchlist(@PathVariable Long id, @RequestParam String status) {
        return watchlistService.updateWatchlist(id, WatchStatus.valueOf(status.toUpperCase()));
    }
    @DeleteMapping("/{id}")
    public String deleteWatchlist(@PathVariable Long id) {
        watchlistService.deleteFromWatchlist(id);
        return "Deleted successfully";
    }
}