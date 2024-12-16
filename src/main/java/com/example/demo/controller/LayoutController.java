package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("layout")
public class LayoutController {
  @GetMapping
  public String index(){
    return "layout/index";
  }
}
