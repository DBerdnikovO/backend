package com.example.backendmovie.enity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "checkout_date")
    private String chekoutDate;
    @Column(name = "return_date")
    private String returnDate;
    @Column(name = "movie_id")
    private Long movieId;

    public Checkout() {
    }

    public Checkout(String userEmail, String checkoutDate, String returnDate, Long movieId) {

        this.userEmail = userEmail;
        this.chekoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.movieId = movieId;

    }
}
