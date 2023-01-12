package com.example.backendmovie.controller;


import com.example.backendmovie.requestmodels.AddMovieRequest;
import com.example.backendmovie.requestmodels.UpdateMovieRequest;
import com.example.backendmovie.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/movie/quantity")
    public void increaseBookQuantity(@RequestParam Long movieId) throws Exception {

        adminService.increaseMovieQuantity(movieId);
    }

    @PutMapping("/secure/decrease/movie/quantity")
    public void decreaseMovieQuantity(@RequestParam Long movieId) throws Exception {
        adminService.decreaseMovieQuantity(movieId);
    }

    @PostMapping("/secure/add/movie")
    public void postMovie(@RequestBody AddMovieRequest addMovieRequest) {
        adminService.postMovie(addMovieRequest);
    }

    @PostMapping("/secure/update/movie")
    public void updateBook(@RequestBody UpdateMovieRequest updateMovieRequest) {
        adminService.updateMovie(updateMovieRequest);
    }

    @DeleteMapping("/secure/delete/movie")
    public void deleteMovie(@RequestParam Long movieId) throws Exception {
        adminService.deleteMovie(movieId);
    }

}
