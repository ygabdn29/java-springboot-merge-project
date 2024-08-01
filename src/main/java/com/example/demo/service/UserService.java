package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.service.generic.GenericService;

public interface UserService extends GenericService<User, Integer> {
  public User authenticate(String username, String password);
}
