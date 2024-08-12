package com.example.demo.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> get() {
        return departmentRepository.findAll();
    }

    @Override
    public Department get(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean save(Department entity) {
        departmentRepository.save(entity);
        return departmentRepository.findById(entity.getId()).isPresent();
    }

    @Override
    public Boolean delete(Integer id) {
        departmentRepository.deleteById(id);
        return departmentRepository.findById(id).isEmpty();
    }

    @Override
    public Department get(String name){
        return departmentRepository.findByName(name);
    }
    
}
