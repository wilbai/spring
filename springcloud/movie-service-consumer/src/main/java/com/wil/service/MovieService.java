package com.wil.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wil.pojo.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wil on 2017/12/27.
 */
@Service
public class MovieService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getMovieFallback")
    public Movie getMovie(Integer id) {
        return restTemplate.getForObject("http://MOVIE-SERVICE-PROVIDER/movie/"+id,Movie.class);
    }

    public Movie getMovieFallback(Integer id) {
        return new Movie(-1,null,null);
    }

}
