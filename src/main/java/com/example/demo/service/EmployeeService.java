package com.example.demo.service;

import com.example.demo.model.Employee;
import com.example.demo.service.generic.GenericService;

public interface EmployeeService extends GenericService<Employee, Integer>{
  Employee findByEmail(String email);
  Employee findById(Integer id);
  
}
