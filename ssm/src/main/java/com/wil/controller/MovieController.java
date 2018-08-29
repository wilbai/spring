package com.wil.controller;

import com.wil.entity.Movie;
import com.wil.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wil on 2018/8/22.
 */
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> list() {
        return movieService.findAll();
    }

    @GetMapping("/{id:\\d+}.json")
    public Movie viewMovie(@PathVariable Integer id) {
        return movieService.findById(id);
    }

    @PostMapping("/new")
    public Movie newMovie(Movie movie) {
        movieService.save(movie);
        return movie;
    }

    @PostMapping("/edit")
    public String update(Movie movie) {
        movieService.update(movie);
        return "success";
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public String remove(@PathVariable Integer id) {
        movieService.delete(id);
        return "success";
    }

}
