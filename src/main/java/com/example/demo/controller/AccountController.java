package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Employee;
import com.example.demo.model.User;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("account")
public class AccountController {
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private EmployeeService employeeService;

  @GetMapping
  public String index(Model model) {
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

  @GetMapping("role")
  public String roleIndex(Model model) {
    model.addAttribute("users", userService.get());

    return "roleManagement/roleManagement";
  }

  @GetMapping("find-username")
  public String formUsername(Model model) {
    return "account/formForgotPassword";
  }

  @PostMapping("forgot-password")
  public String processForgotPassword(String email, Model model) {
    Employee employee = employeeService.findByEmail(email);
    if (employee == null) {
      model.addAttribute("error", "Username not found");
      return "account/formForgotPassword";
    }    
    model.addAttribute("email", email);
    return "account/resetPassword";
  }

  @GetMapping("{id}/role")
  public String roleEdit(@PathVariable Integer id, Model model){
    model.addAttribute("user", userService.get(id));
    model.addAttribute("roles", roleService.get());

    return "roleManagement/roleUpdate";
  }

  @PostMapping("role/update")
  public String roleUpdate(User user){
    if(user.getId() != null){
      return userService.save(user) ? "redirect:/userlogin/role" : "error";
    } 
    return "error";
  }

  @PostMapping("reset-password")
  public String processResetPassword(String email, String password, Model model) {
    Employee employee = employeeService.findByEmail(email);
    if (employee == null) {
      model.addAttribute("error", "Invalid email.");
      return "account/resetPassword";
    }

    Integer employeeId = employee.getId();
    User user = userService.get(employeeId);
    if (user == null) {
      model.addAttribute("error", "User not found.");
      return "account/resetPassword";
    }

    user.setPassword(password);
    userService.save(user);

    return "login/indexlogin";
  }
}
