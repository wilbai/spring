package com.wil.controller;

import com.wil.client.MovieClient;
import com.wil.pojo.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by wil on 2017/12/27.
 */
@RestController
public class HomeController {

    @Autowired
    private RestTemplate template;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private MovieClient movieClient;

//    @GetMapping("/movie/{id:\\d+}")
//    public Movie showMovieInfo(@PathVariable Integer id) {
//        String url = "http://127.0.0.1:8080/movie/"+id;
//        return template.getForObject(url,Movie.class);
//    }

//    @GetMapping("/movie/{id:\\d+}")
//    public Movie showMovieInfo(@PathVariable Integer id) {
//        ServiceInstance serviceInstance = loadBalancerClient.choose("MOVIE-SERVICE-PROVIDER");
//        String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/movie/"+id;
//        return template.getForObject(url,Movie.class);
//    }

    // Ribbon
//    @GetMapping("/movie/{id:\\d+}")
//    public Movie showMovieInfo(@PathVariable Integer id) {
//        String url = "http://MOVIE-SERVICE-PROVIDER/movie/"+id;
//        return template.getForObject(url,Movie.class);
//    }

    @GetMapping("/movie/{id:\\d+}")
    public Movie showMovieInfo(@PathVariable Integer id) {
        return movieClient.findById(id);
    }


}
