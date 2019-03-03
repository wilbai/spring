package com.wil.client;

import com.wil.pojo.Movie;
import com.wil.service.MovieServiceFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by wil on 2017/12/27.
 */
@FeignClient(name = "MOVIE-SERVICE-PROVIDER",fallback = MovieServiceFallback.class)
public interface MovieClient {

    @GetMapping("/movie/{id}")
    Movie findById(@PathVariable(name = "id") Integer id);

}
