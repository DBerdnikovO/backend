package com.example.backendmovie.requestmodels;

import lombok.Data;

@Data
public class UpdateMovieRequest {

    private Long id;

    private String title;

    private String author;

    private String description;

    private int copies;

    private String category;

    private String img;
}
