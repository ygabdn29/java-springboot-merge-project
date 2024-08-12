package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.service.generic.GenericService;

public interface DepartmentService extends GenericService<Department, Integer>{
    public Department get(String name);
}
