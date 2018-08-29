package com.wil.mapper;

import com.wil.entity.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Movie record);

    Movie selectByPrimaryKey(Integer id);

    List<Movie> selectAll();

    int updateByPrimaryKey(Movie record);
}