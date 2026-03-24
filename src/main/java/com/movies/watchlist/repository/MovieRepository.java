package com.movies.watchlist.repository;

import com.movies.watchlist.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(String genre); //Get by genre
}