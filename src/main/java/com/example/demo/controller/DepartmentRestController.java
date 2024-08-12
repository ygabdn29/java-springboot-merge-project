package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Department;
import com.example.demo.model.dto.DepartmentDTO;
import com.example.demo.handler.Utils;

import com.example.demo.service.DepartmentService;
import com.example.demo.service.RegionService;

@RestController
@RequestMapping("api/department")
public class DepartmentRestController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    RegionService regionService;

    @GetMapping("get-all")
    public ResponseEntity<Object> index(@RequestHeader("x-token-department") String token) {
        if (token.equals("Abcdefgh")) {
            List<Department> departments = departmentService.get();
            return Utils.generateResponseEntity(HttpStatus.OK, "Department's data have been retrieved", departments);
        }

        return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Failed to get data, invalid token");
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Object> getId(@PathVariable Integer id, @RequestHeader("x-token-department") String token,
            Department department) {
        if (token.equals("Abcdefgh")) {
            return Utils.generateResponseEntity(HttpStatus.OK, "Department's data have been retrieved",
                    departmentService.get(id));
        }
        return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Failed to get data, invalid token");
    }

    @PostMapping("save")
    public ResponseEntity<Object> save(@RequestBody DepartmentDTO departmentDto,
            @RequestHeader("x-token-department") String token) {
        Department department;
        if (token.equals("Abcdefgh")) {
            if (departmentDto.getId() != null) {
                department = departmentService.get(departmentDto.getId());
            } else {
                department = new Department();
            }
            department.setName(departmentDto.getName());
            department.setRegion(regionService.get(departmentDto.getRegion_id()));
            departmentService.save(department);
            return Utils.generateResponseEntity(HttpStatus.OK, "Department's data have been saved", departmentDto);
        }
        return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Failed to save data, invalid token");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id, @RequestHeader("x-token-department") String token) {
        if (token.equals("Abcdefgh")) {
            return Utils.generateResponseEntity(HttpStatus.OK, "Data has been deleted", departmentService.delete(id));
        }
        return Utils.generateResponseEntity(HttpStatus.BAD_REQUEST, "Failed to delete data, invalid token");
    }
}
