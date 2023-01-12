package com.example.backendmovie.requestmodels;

import lombok.Data;

@Data
public class AddMovieRequest {

    private String title;

    private String author;

    private String description;

    private int copies;

    private String category;

    private String img;
}
