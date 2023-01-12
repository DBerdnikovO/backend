package com.example.backendmovie.controller;


import com.example.backendmovie.requestmodels.ReviewRequest;
import com.example.backendmovie.service.ReviewService;
import com.example.backendmovie.utils.ExtractJWT;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/movie")
    public Boolean reviewMovieByUser(@RequestHeader(value = "Authorization") String token,
                                     @RequestParam Long movieId) throws Exception {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token, "\"sub\"");

        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, movieId);
    }

    @PostMapping("/secure")
    public void postReview(@RequestHeader(value = "Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest) throws Exception {
        String userEmail = ExtractJWT.payLoadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
}
