package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.model.User;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("userlogin")
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("testlogin")
    public String index(Model model){
    model.addAttribute("users", userService.get());
    return "login/indexlogin";
   }


   @PostMapping("login")
   public String login(@RequestParam String username, @RequestParam String password, Model model) {
       User user = userService.authenticate(username, password);
       if (user != null) {
           model.addAttribute("user", user);
           return "login/welcome"; // Redirect ke welcome page
       } else {
           model.addAttribute("error", "Invalid username or password");
           return "login/indexlogin"; // Redirect balikin ke halaman login
       }
   }

   @GetMapping("register")
   public String register(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("user", new User());
        return "account/register";
   }
   

}