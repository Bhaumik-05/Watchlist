package com.movies.watchlist.service;

import com.movies.watchlist.entity.Watchlist;
import com.movies.watchlist.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movies.watchlist.entity.User;
import com.movies.watchlist.entity.Movie;
import com.movies.watchlist.repository.UserRepository;
import com.movies.watchlist.repository.MovieRepository;


import java.util.List;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Watchlist addToWatchlist(Watchlist watchlist) {

        Long userId = watchlist.getUser().getId();
        Long movieId = watchlist.getMovie().getId();

        // fetch full objects from DB
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // set full objects
        watchlist.setUser(user);
        watchlist.setMovie(movie);

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