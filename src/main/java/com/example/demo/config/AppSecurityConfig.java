package com.example.demo.config;

import javax.management.RuntimeErrorException;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurityConfig {

  // Authentication
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
  }

  // Authorization
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
      http  
            .csrf().disable()
            .authorizeRequests((auth) -> {
                try {
                    auth
                            .antMatchers("/").permitAll()
                            .antMatchers("/account/**").permitAll()
                            .antMatchers("/account/welcome").authenticated()
                            .antMatchers("/account/find-email").permitAll()
                            .antMatchers("/account/role").authenticated()
                            .antMatchers("/api/account/**").permitAll()
                            .anyRequest().permitAll()
                            .and()
                            .formLogin()
                            .loginPage("/account/formlogin")
                            .and()
                            .httpBasic()
                            .and()
                            .logout()
                            .logoutUrl("/account/logout")
                            .logoutSuccessUrl("/account/formlogin")
                            .permitAll();
                            

                } catch (Exception e) {
                    throw new RuntimeErrorException(null);
                }
           
            });

            return http.build();
    }

  // tambahan library menggunakan BCrypt
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

}
