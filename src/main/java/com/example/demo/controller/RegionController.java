package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("region2")
public class RegionController {
    @GetMapping
    public String regionhtml(){
        return "region/index";
    }
}
