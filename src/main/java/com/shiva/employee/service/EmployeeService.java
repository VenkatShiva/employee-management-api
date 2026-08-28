package com.shiva.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shiva.employee.model.Employee;
import com.shiva.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee) {

        this.employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

}
