package com.movies.watchlist.service;

import com.movies.watchlist.entity.Watchlist;
import com.movies.watchlist.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    public Watchlist addToWatchlist(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public List<Watchlist> getUserWatchlist(Long userId) {
        return watchlistRepository.findByUserId(userId);
    }

    public Watchlist updateWatchlist(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public void deleteFromWatchlist(Long id) {
        watchlistRepository.deleteById(id);
    }
}