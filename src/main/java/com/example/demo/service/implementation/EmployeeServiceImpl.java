package com.example.demo.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public List<Employee> get() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee get(Integer id) {
    return employeeRepository.findById(id).orElse(null);
  }

  @Override
  public Boolean save(Employee entity) {
    employeeRepository.save(entity);
    return employeeRepository.findById(entity.getId()).isPresent();
  }

  @Override
  public Boolean delete(Integer id) {
    employeeRepository.deleteById(id);
    return employeeRepository.findById(id).isEmpty();
  }

  @Override
  public Employee findByEmail(String email) {
    return employeeRepository.findByEmail(email);
  }
}
