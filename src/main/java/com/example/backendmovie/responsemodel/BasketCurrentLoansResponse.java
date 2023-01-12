package com.example.backendmovie.responsemodel;

import com.example.backendmovie.enity.Movie;
import lombok.Data;

@Data
public class BasketCurrentLoansResponse {

    private Movie movie;

    private int daysLeft;

    public BasketCurrentLoansResponse(Movie movie, int daysLeft) {
        this.movie = movie;
        this.daysLeft = daysLeft;
    }

}
