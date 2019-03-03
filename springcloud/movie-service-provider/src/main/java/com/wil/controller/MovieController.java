package com.wil.controller;

import com.wil.pojo.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wil on 2017/12/27.
 */
@RestController
public class MovieController {

    @GetMapping("/movie/{id:\\d+}")
    public Movie findById(@PathVariable Integer id) {
        return new Movie(1001,"无间道II","Andy Liu");
    }


}
