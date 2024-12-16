package com.example.demo.controller;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.handler.Utils;
import com.example.demo.model.Employee;
import com.example.demo.model.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.dto.RegistrationDTO;
import com.example.demo.service.EmailService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("api/account")
public class AccountRestController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EmailService emailService;

//   Nilam
  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody RegistrationDTO registrationDTO) {
    try {
      Employee employee = new Employee(null, registrationDTO.getName(), registrationDTO.getEmail(), registrationDTO.getBirthDate(), registrationDTO.getAddress(), registrationDTO.getPhone());
      employeeService.save(employee);

      Role defaultRole = roleService.getRoleWithLowestLevel();
      String guid = UUID.randomUUID().toString();
      User user = new User(registrationDTO.getPassword(), registrationDTO.getUsername(), defaultRole, false, null, employee);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setEmployee(employee);
      user.setGuid(guid);
      userService.save(user);

      String subject = "Email Verification";
      String confirmationUrl = "http://localhost:8080/api/account/verify/" + user.getGuid();
      String message = "Click the link to verify your email: \n" + confirmationUrl;
      emailService.sendEmail(employee.getEmail(), subject, message);

      return Utils.generaResponseEntity(HttpStatus.OK,
          "Registration Successful. A verification email has been sent to your email address.");
    } catch (Exception e) {
      return Utils.generaResponseEntity(HttpStatus.OK,
          "Registration Failed: " + e.getMessage());
    }
  }

  @PostMapping("/verify/{guid}")
  public ResponseEntity<Object> verifyEmail(@PathVariable String guid) {
    User user = userService.verifyUser(guid);
    if (user != null) {
      user.setIsVerified(true);
      user.setGuid(null);
      userService.save(user);
      return Utils.generaResponseEntity(HttpStatus.OK, "Verification Account successfully");
    }
    return Utils.generaResponseEntity(HttpStatus.OK, "Verification Failed");
  }

   
//  Anggia
    @GetMapping("list")
    public ResponseEntity<Object> get(@RequestHeader("X-token-account-key") String token ){
        if(token.equals("123456789")){
            List<User> users = userService.get();
            return Utils.generateResponseEntity(HttpStatus.OK, "Data has been retrieved", users);
        }
        return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Data can't be accessed. Token is invalid");
    } 

    @PostMapping("role/save")
    public ResponseEntity<Object> save(@RequestHeader("X-token-account-key") String token, @RequestBody UserDto userDto){
        if(token.equals("123456789")){
            User user2 = userService.get(userDto.getEmployee_id());
            user2.setRole(roleService.get(userDto.getRole_id()));
            userService.save(user2);
            return Utils.generateResponseEntity(HttpStatus.OK, "Role has been assigned", userDto);
        }
        return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Role can't be assigned. Token is invalid");
    }

//   reza
    @GetMapping("password") //kirim email
    public ResponseEntity<Object> sendEmailChangePassword(@RequestBody User user) { 
        User validUser = userService.authenticate(user.getUsername(), user.getPassword());
        if(validUser != null){
            String guid = UUID.randomUUID().toString();
            validUser.setGuid(guid); // set user lokal
            userService.save(validUser); //update ke db
            String linkEmailGuid = "http://localhost:8080/api/account/password/change/" + guid;
            emailService.sendEmail(validUser.getEmployee().getEmail(), "Change password", "Please click the link below to change your password\n" + linkEmailGuid);
            return Utils.generateResponseEntity(HttpStatus.OK, "We've sent you an email");
        }
        else{
            return Utils.generateResponseEntity(HttpStatus.OK, "Wrong username or password");
        }
    }

    @PostMapping("password/change/{guid}")
    public ResponseEntity<Object> changePasswordGuid(@PathVariable String guid, @RequestHeader String recentPass, 
                                                    @RequestHeader String newPass, @RequestHeader String confirmPass){
        User user = userService.findByStringGuid(guid);
        User validatedUser = userService.validatePassword(user, recentPass, newPass, confirmPass);
        if(validatedUser == null){
            user.setGuid(null);
            userService.save(user);
            return Utils.generateResponseEntity(HttpStatus.OK, "Invalid Password or Unmatch Password");
        }
        validatedUser.setPassword(passwordEncoder.encode(newPass));
        validatedUser.setGuid(null);
        userService.save(validatedUser);
        return Utils.generateResponseEntity(HttpStatus.OK, "Password successfully has been changed");
    }
    
// Yoga
  @PostMapping("login")
  public ResponseEntity<Object> login(@RequestBody User userLogin ){
    User auntheticatedUser = userService.authenticate(userLogin.getUsername(), userLogin.getPassword());

    if(auntheticatedUser != null && !auntheticatedUser.getIsVerified()){
      return Utils.generaResponseEntity(HttpStatus.OK, "Not Verified Yet!");
    }

    try{ 
      org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
      auntheticatedUser.getId().toString(),
      "",
      getAuthorities(auntheticatedUser.getRole().getName())
    );

    PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
      user,
      "", 
      user.getAuthorities()
      );
    
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

  

    return Utils.generaResponseEntity(HttpStatus.OK, "Login Success!");
    } catch(Exception e){
      return Utils.generaResponseEntity(HttpStatus.OK, "Credentials Doesn't Match Any Records!");
    }
  }

  private static Collection<? extends GrantedAuthority> getAuthorities(String role){

    final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    return authorities;
  }

// Aten
    @PostMapping("findEmail")
    public ResponseEntity<String> processForgotPassword(@RequestBody Employee employee) {
        // Extract email from the request body
        String email = employee.getEmail();
        
        // Validate email parameter
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                                 .body("Email parameter is missing or empty");
        }

        // Find employee by email
        Employee foundEmployee = employeeService.findByEmail(email);

        // Check if the employee is found and return the ID
        if (foundEmployee != null) {
            return ResponseEntity.ok("Employee ID: " + foundEmployee.getId() + " Email: " + foundEmployee.getEmail());
            
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Employee not found");
        }


    }

    @PostMapping("validate-password")
    public ResponseEntity<Object> validatePassword(@RequestBody String password) {
        if (userService.isValidPassword(password)) {
            return Utils.generateResponseEntity(HttpStatus.OK, "Password Valid");
        } else {
            return Utils.generateResponseEntity(HttpStatus.OK, "Password tidak valid. Password harus memiliki minimal 8 karakter, mengandung huruf besar, huruf kecil, dan angka.");
        }
    }

    @PostMapping("reset-password")
    public ResponseEntity<Object> processResetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (email == null || password == null) {
            return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Email and password are required.");
        }

        Employee foundEmployee = employeeService.findByEmail(email);
        if (foundEmployee == null) {
            return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        Integer employeeId = foundEmployee.getId();
        User user = userService.get(employeeId);
        if (user == null) {
            return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "User not found.");
        }

        user.setPassword(passwordEncoder.encode(password));
        userService.save(user);

        return Utils.generateResponseEntity(HttpStatus.OK, "Password has been reset successfully.");
    }
    
}
