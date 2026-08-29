package com.shiva.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shiva.employee.dto.EmployeeRecord;
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

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        return new ResponseEntity<>(this.employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Map<String, String>> addEmployee(@Valid @RequestBody Employee employee) {
        Map<String, String> resp = new HashMap<>();
        this.employeeService.addEmployee(employee);

        resp.put("status", "Success");

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(this.employeeService.getEmployee(id), HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Map<String, String>> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody EmployeeRecord record) {
        Map<String, String> resp = new HashMap<>();
        this.employeeService.updateEmployee(id, record);
        resp.put("status", "Success");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
