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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiva.employee.dto.AddSkillRequest;
import com.shiva.employee.dto.CreateEmployeeRequest;
import com.shiva.employee.dto.EmployeeResponse;
import com.shiva.employee.dto.UpdateEmployeeRequest;
import com.shiva.employee.service.EmployeeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Employee Management API is running";
    }

    @GetMapping("")
    public ResponseEntity<List<EmployeeResponse>> getEmployeesFilter(@RequestParam(required = false) String name,
            @RequestParam(required = false) String department) {

        if (name != null && department != null && !name.isBlank() && !department.isBlank()) {
            return new ResponseEntity<>(this.employeeService.getByNameAndDepartment(name, department), HttpStatus.OK);
        } else if (name != null && !name.isBlank()) {
            return new ResponseEntity<>(this.employeeService.getByName(name), HttpStatus.OK);
        } else if (department != null && !department.isBlank()) {
            return new ResponseEntity<>(this.employeeService.getByDepartment(department), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchByName(@Valid @RequestParam @NotBlank String name) {
        return ResponseEntity.ok(this.employeeService.getByNameContains(name));
    }

    @GetMapping("/search/salary")
    public ResponseEntity<List<EmployeeResponse>> searchBySalary(@Valid @RequestParam @NotNull Long min) {
        return ResponseEntity.ok(this.employeeService.getBySalaryGreaterThan(min));
    }

    @GetMapping("/search/department")
    public ResponseEntity<List<EmployeeResponse>> searchByDepartmentOrderBySalaryDesc(
            @Valid @RequestParam @NotBlank String department) {
        return ResponseEntity.ok(this.employeeService.getByDepartmentOrderBySalaryDesc(department));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> addEmployee(@Valid @RequestBody CreateEmployeeRequest employee) {
        Map<String, String> resp = new HashMap<>();
        this.employeeService.addEmployee(employee);

        resp.put("status", "Success");

        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(this.employeeService.getEmployee(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateEmployee(@PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequest updateRequest) {
        Map<String, String> resp = new HashMap<>();
        this.employeeService.updateEmployee(id, updateRequest);
        resp.put("status", "Success");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/skill/{id}")
    public ResponseEntity<Map<String, String>> addSkillEmployee(@PathVariable Long id,
            @Valid @RequestBody AddSkillRequest updateRequest) {
        Map<String, String> resp = new HashMap<>();
        this.employeeService.addSkill(id, updateRequest.name());
        resp.put("status", "Success");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/search/skill")
    public ResponseEntity<List<EmployeeResponse>> searchBySkill(
            @Valid @RequestParam @NotBlank String name) {
        return ResponseEntity.ok(this.employeeService.getBySkill(name));
    }

}
