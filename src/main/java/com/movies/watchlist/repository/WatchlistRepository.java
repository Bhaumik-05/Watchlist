package com.movies.watchlist.repository;

import com.movies.watchlist.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    List<Watchlist> findByUserId(Long userId);
    List<Watchlist> findByMovieId(Long watchlistId);
    Optional<Watchlist> findByUserIdAndMovieId(Long userId, Long movieId);
}