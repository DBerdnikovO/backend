package com.example.backendmovie.service;

import com.example.backendmovie.dao.ReviewRepository;
import com.example.backendmovie.enity.Review;
import com.example.backendmovie.requestmodels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndMovieId(userEmail, reviewRequest.getMovieId());
        if (validateReview != null) {
            throw new Exception("Review already created");
        }

        Review review = new Review();
        review.setMovieId(reviewRequest.getMovieId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if (reviewRequest.getReviewDescription().isPresent()) {
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString
            ).orElse(null));
        }
        review.setDate(Date.valueOf(LocalDate.now()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long movieId) {
        Review validateReview = reviewRepository.findByUserEmailAndMovieId(userEmail, movieId);
        return validateReview != null;
    }
}
