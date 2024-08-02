package com.example.demo5.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.model.Employee;
import com.example.demo.model.Role;
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

    @GetMapping("formlogin")
    public String index(Model model, HttpSession session) {
        // Check if user is already logged in
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser != null) {
            model.addAttribute("user", loggedInUser);
            return "login/welcome"; // If logged in, redirect to welcome page
        }
        model.addAttribute("users", userService.get());
        return "login/indexlogin";
    }

  @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.authenticate(username, password); // Authenticate user
        if (user != null) {
            session.setAttribute("user", user); // Store user in session
            String sessionId = session.getId();
            Integer setId = user.getId();
            return "redirect:welcome/" + setId + ";jsessionid=" + sessionId; // Redirect with user ID and session ID
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login/indexlogin"; // Redirect back to login page
        }
    }
  
  @GetMapping("welcome/{userId}")
    public String welcome(@PathVariable Integer userId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null || !loggedInUser.getId().equals(userId)) {
            return "redirect:testlogin";
        }
        model.addAttribute("user", loggedInUser);
        model.addAttribute("userId", loggedInUser.getId());
        return "login/welcome";
    }

  @GetMapping("session/{userId}")
    public String getSessionId(@PathVariable Integer userId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null || !loggedInUser.getId().equals(userId)) {
            return "redirect:indexlogin";
        }
        String sessionId = session.getId();
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("userId", userId);
        return "login/session";
    }
  
  @GetMapping("role")
  public String roleIndex(Model model) {
    model.addAttribute("users", userService.get());

    return "roleManagement/roleManagement";
  }

  @GetMapping("find-email")
  public String formEmail(Model model) {
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


   @GetMapping("register")
   public String register(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("user", new User());
        return "account/register";
   }

   @PostMapping("save")
   public String save(User user) {
        Role defaultRole = roleService.get(2); // EMPLOYEE ROLE (LOWEST LEVEL)        
        employeeService.save(user.getEmployee());      
        user.setRole(defaultRole);  
        return userService.save(user) ? "redirect:/account" : "account/register";
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
      return userService.save(user) ? "redirect:/account/role" : "error";
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

