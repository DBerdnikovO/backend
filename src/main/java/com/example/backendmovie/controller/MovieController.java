package com.example.backendmovie.controller;

import com.example.backendmovie.enity.Movie;
import com.example.backendmovie.responsemodel.BasketCurrentLoansResponse;
import com.example.backendmovie.service.MovieService;
import com.example.backendmovie.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/secure/currentLoans")
    public List<BasketCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws Exception {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token, "\"sub\"");
        return movieService.currentLoans(userEmail);
    }

    @GetMapping("/secure/currentLoans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token, "\"sub\"");
        return movieService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutMovieByUser(@RequestHeader(value = "Authorization") String token,
                                       @RequestParam Long movieId) {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token, "\"sub\"");
        return movieService.checkoutMovieByUser(userEmail, movieId);
    }

    @PutMapping("/secure/checkout")
    public Movie checkoutMovie(@RequestHeader(value = "Authorization") String token,
                               @RequestParam Long movieId) throws Exception {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token, "\"sub\"");
        return movieService.chekoutMovie(userEmail, movieId);
    }

    @PutMapping("/secure/return")
    public void returnMovie(@RequestHeader(value = "Authorization") String token,
                               @RequestParam Long movieId) throws Exception {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token, "\"sub\"");
        movieService.returnMovie(userEmail, movieId);
    }

}
