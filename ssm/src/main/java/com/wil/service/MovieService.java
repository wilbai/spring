package com.wil.service;

import com.wil.entity.Movie;

import java.util.List;

/**
 * Created by wil on 2018/8/22.
 */
public interface MovieService {

    List<Movie> findAll();

    Movie findById(Integer id);

    void save(Movie movie);

    void update(Movie movie);

    void delete(Integer id);
}
