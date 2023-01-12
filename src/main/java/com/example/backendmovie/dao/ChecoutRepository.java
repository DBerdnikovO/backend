package com.example.backendmovie.dao;

import com.example.backendmovie.enity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChecoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndMovieId(String userEmail, Long movieId);

    List<Checkout> findMovieByUserEmail(String userEmail);

    @Modifying
    @Query("delete from Checkout where movieId in :movie_id")
    void deleteAllByMovieId(@Param("movie_id") Long movieId);

}
