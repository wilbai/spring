package com.wil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by wil on 2017/12/25.
 */
@Controller
public class MapPointController {

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping("/point")
    public String point() {
        return "point";
    }

}
