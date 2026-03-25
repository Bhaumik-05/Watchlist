package com.movies.watchlist.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.movies.watchlist.entity.Watchlist;
import com.movies.watchlist.entity.User;
import com.movies.watchlist.entity.Movie;
import com.movies.watchlist.enums.WatchStatus;
import com.movies.watchlist.repository.WatchlistRepository;
import com.movies.watchlist.repository.UserRepository;
import com.movies.watchlist.repository.MovieRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    // We need UserRepository and MovieRepository here because when the client
    // sends a watchlist request, they only send { user: {id: 1}, movie: {id: 2} }
    // We need to fetch the FULL User and Movie objects from DB before saving
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Watchlist addToWatchlist(Watchlist watchlist) {

        // Step 1: Extract just the IDs from the partial objects sent by client
        Long userId = watchlist.getUser().getId();
        Long movieId = watchlist.getMovie().getId();

        // Step 2: Fetch the full User object from database using that id
        // If no user found with that id, throw an exception (caught by GlobalExceptionHandler)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Step 3: Same for Movie
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        // Step 4: Set the full objects back into the watchlist
        // This ensures JPA saves proper foreign key references in the DB
        watchlist.setUser(user);
        watchlist.setMovie(movie);

        // Step 5: Auto-set today's date so client doesn't have to send it manually
        // LocalDate.now() gives the current date (no time)
        watchlist.setAddedDate(LocalDate.now());

        // Step 6: Save and return the complete saved object
        return watchlistRepository.save(watchlist);
    }

    // Fetch all watchlist entries belonging to a specific user
    // findByUserId() is a derived query in the repository —
    // Spring auto-generates: SELECT * FROM watchlist WHERE user_id = ?
    public List<Watchlist> getUserWatchlist(Long userId) {
        return watchlistRepository.findByUserId(userId);
    }

    // FIXED: Instead of blindly saving whatever the client sends (dangerous),
    // we now:
    //   1. Fetch the existing entry from DB
    //   2. Only update the STATUS field
    //   3. Save it back
    // This prevents the client from changing user/movie/date during an update
    public Watchlist updateWatchlist(Long id, WatchStatus newStatus) {

        // Fetch the existing record — throw error if not found
        Watchlist existing = watchlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Watchlist entry not found with id: " + id));

        // Only allow changing the status (PLANNED → WATCHED etc.)
        existing.setStatus(newStatus);

        // Save the modified object back — JPA does UPDATE since id already exists
        return watchlistRepository.save(existing);
    }

    // Delete a watchlist entry by id
    public void deleteFromWatchlist(Long id) {
        watchlistRepository.deleteById(id);
    }
}