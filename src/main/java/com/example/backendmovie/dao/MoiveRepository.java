package com.example.backendmovie.dao;

import com.example.backendmovie.enity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MoiveRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);

    Page<Movie> findByCategory(@RequestParam("category") String category, Pageable pageable);

    @Query("select o from Movie o where o.id in :movie_ids")
    List<Movie> findMoviesByMoiveIds (@Param("movie_ids")List<Long> movieId);

}
