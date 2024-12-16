package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.service.generic.GenericService;


public interface RoleService extends GenericService<Role, Integer>  {
  public Role getRoleWithLowestLevel();
}
