package com.example.backendmovie.service;

import com.example.backendmovie.dao.ChecoutRepository;
import com.example.backendmovie.dao.MoiveRepository;
import com.example.backendmovie.dao.ReviewRepository;
import com.example.backendmovie.enity.Movie;
import com.example.backendmovie.requestmodels.AddMovieRequest;
import com.example.backendmovie.requestmodels.UpdateMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private final MoiveRepository moiveRepository;
    private final ReviewRepository reviewRepository;
    private final ChecoutRepository checkoutRepository;

    @Autowired
    public AdminService (MoiveRepository moiveRepository,
                         ReviewRepository reviewRepository,
                         ChecoutRepository checkoutRepository) {
        this.moiveRepository = moiveRepository;
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public void increaseMovieQuantity(Long movieId) throws Exception {

        Optional<Movie> movie = moiveRepository.findById(movieId);

        if (movie.isEmpty()) {
            throw new Exception("Movie not found");
        }

        movie.get().setCopiesAvailable(movie.get().getCopiesAvailable() + 1);
        movie.get().setCopies(movie.get().getCopies() + 1);

        moiveRepository.save(movie.get());
    }

    public void decreaseMovieQuantity(Long movieId) throws Exception {

        Optional<Movie> movie = moiveRepository.findById(movieId);

        if(movie.isEmpty() || movie.get().getCopiesAvailable() <= 0 || movie.get().getCopies() <= 0) {
            throw new Exception("Movie not found or quantity locked");
        }

        movie.get().setCopiesAvailable(movie.get().getCopiesAvailable() - 1);
        movie.get().setCopies(movie.get().getCopies() - 1);

        moiveRepository.save(movie.get());
    }

    public void postMovie(AddMovieRequest addMovieRequest) {
        Movie movie = new Movie();
        movie.setTitle(addMovieRequest.getTitle());
        movie.setAuthor(addMovieRequest.getAuthor());
        movie.setDescription(addMovieRequest.getDescription());
        movie.setCopies(addMovieRequest.getCopies());
        movie.setCopiesAvailable(addMovieRequest.getCopies());
        movie.setCategory(addMovieRequest.getCategory());
        movie.setImg(addMovieRequest.getImg());
        moiveRepository.save(movie);
    }

    public void updateMovie(UpdateMovieRequest updateMovieRequest) {
        Movie movie = new Movie();
        movie.setId(updateMovieRequest.getId());
        movie.setTitle(updateMovieRequest.getTitle());
        movie.setAuthor(updateMovieRequest.getAuthor());
        movie.setDescription(updateMovieRequest.getDescription());
        movie.setCopies(updateMovieRequest.getCopies());
        movie.setCopiesAvailable(updateMovieRequest.getCopies());
        movie.setCategory(updateMovieRequest.getCategory());
        movie.setImg(updateMovieRequest.getImg());
        moiveRepository.save(movie);
    }

    public void deleteMovie(Long movieId) throws Exception {

        Optional<Movie> movie = moiveRepository.findById(movieId);

        if (movie.isEmpty()) {
            throw new Exception("Movie not found");
        }

        moiveRepository.delete(movie.get());
        checkoutRepository.deleteAllByMovieId(movieId);
        reviewRepository.deleteAllByMovieId(movieId);
    }

}
