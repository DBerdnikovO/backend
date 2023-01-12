package com.example.backendmovie.service;

import com.example.backendmovie.dao.ChecoutRepository;
import com.example.backendmovie.dao.MoiveRepository;
import com.example.backendmovie.enity.Checkout;
import com.example.backendmovie.enity.Movie;
import com.example.backendmovie.responsemodel.BasketCurrentLoansResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class MovieService {

    private final MoiveRepository movieRepository;

    private final ChecoutRepository checoutRepository;

    public MovieService(MoiveRepository movieRepository, ChecoutRepository checoutRepository) {
        this.movieRepository = movieRepository;
        this.checoutRepository = checoutRepository;
    }

    public Movie chekoutMovie(String userEmail, Long movieId) throws Exception {

        Optional<Movie> movie = movieRepository.findById(movieId);

        Checkout validateCheckout = checoutRepository.findByUserEmailAndMovieId(userEmail, movieId);

        if (movie.isEmpty() || validateCheckout != null || movie.get().getCopiesAvailable() <= 0) {
            throw new Exception("Movie doesn't exist or already checked out by user");
        }

        movie.get().setCopiesAvailable(movie.get().getCopiesAvailable() - 1);
        movieRepository.save(movie.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                movie.get().getId()
        );

        checoutRepository.save(checkout);

        return movie.get();

    }

    public Boolean checkoutMovieByUser(String userEmail, Long movieId) {
        Checkout validateCheckout = checoutRepository.findByUserEmailAndMovieId(userEmail, movieId);
        return validateCheckout != null;
    }

    public int currentLoansCount(String userEmail) {
        return checoutRepository.findMovieByUserEmail(userEmail).size();
    }

    public List<BasketCurrentLoansResponse> currentLoans(String userEmail) throws Exception {

        List<BasketCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>();

        List<Checkout> checkoutList = checoutRepository.findMovieByUserEmail(userEmail);
        List<Long> movieIdList = new ArrayList<>();

        for (Checkout i : checkoutList) {
            movieIdList.add(i.getMovieId());
        }

        List<Movie> movies = movieRepository.findMoviesByMoiveIds(movieIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Movie movie : movies) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getMovieId() == movie.getId()).findFirst();

            if (checkout.isPresent()) {

                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                        TimeUnit.MILLISECONDS);

                shelfCurrentLoansResponses.add(new BasketCurrentLoansResponse(movie, (int) difference_In_Time));
            }
        }
        return shelfCurrentLoansResponses;
    }

    public void returnMovie (String userEmail, Long movieId) throws Exception {
        Optional<Movie> movie = movieRepository.findById(movieId);

        Checkout validateCheckout = checoutRepository.findByUserEmailAndMovieId(userEmail, movieId);

        if(movie.isEmpty() || validateCheckout == null) {
            throw new Exception("Movie does not exist or not checked out by user");
        }

        movie.get().setCopiesAvailable(movie.get().getCopiesAvailable()+1);

        movieRepository.save(movie.get());
        checoutRepository.deleteById(validateCheckout.getId());

    }
}
