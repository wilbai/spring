package com.wil.service;

import com.wil.client.MovieClient;
import com.wil.pojo.Movie;
import org.springframework.stereotype.Component;

/**
 * Created by wil on 2019/2/28.
 */
@Component
public class MovieServiceFallback implements MovieClient {

    @Override
    public Movie findById(Integer id) {
        return new Movie(0,null,null);
    }

}
