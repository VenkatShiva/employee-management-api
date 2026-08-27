package com.shiva.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shiva.employee.model.Employee;
import com.shiva.employee.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Employee Management API is running";
    }

    @PostMapping("/employees")
    public String addEmployee(@Valid @RequestBody Employee employee) {
        this.employeeService.addEmployee(employee);
        return "Employee added successfully";
    }

}
