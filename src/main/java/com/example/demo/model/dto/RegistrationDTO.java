package com.example.demo.model.dto;

import java.time.LocalDate;

public class RegistrationDTO {
  private String username;
  private String password;
  private String name;
  private String email;
  private String address;
  private String phone;
  private LocalDate birthDate;

  public RegistrationDTO(String username, String password, String name, String email, String address, String phone,
      LocalDate birthDate) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.email = email;
    this.address = address;
    this.phone = phone;
    this.birthDate = birthDate;
  }
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }
  public LocalDate getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }
  
  
}
