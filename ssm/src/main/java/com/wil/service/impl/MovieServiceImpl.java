package com.wil.service.impl;

import com.wil.entity.Movie;
import com.wil.mapper.MovieMapper;
import com.wil.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wil on 2018/8/22.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<Movie> findAll() {
        return movieMapper.selectAll();
    }

    @Override
    public Movie findById(Integer id) {
        return movieMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Movie movie) {
        movieMapper.insert(movie);
    }

    @Override
    public void update(Movie movie) {
        movieMapper.updateByPrimaryKey(movie);
    }

    @Override
    public void delete(Integer id) {
        movieMapper.deleteByPrimaryKey(id);
    }


}
