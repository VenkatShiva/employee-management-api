package com.shiva.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shiva.employee.dto.CreateEmployeeRequest;
import com.shiva.employee.dto.UpdateEmployeeRequest;
import com.shiva.employee.exception.EmployeeNotFoundException;
import com.shiva.employee.model.Employee;
import com.shiva.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private Long nextId = 0L;

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(CreateEmployeeRequest createRequest) {
        Employee employee = new Employee(++nextId, createRequest.name(), createRequest.department());
        this.employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    public Employee getEmployee(Long id) {
        return this.employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("Employee not found");
        });
    }

    public void deleteEmployee(Long id) {

        this.employeeRepository.delete(id);
    }

    public void updateEmployee(Long id, UpdateEmployeeRequest updateRequest) {

        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("Employee not found");
        });

        employee.setName(updateRequest.name());
        employee.setDepartment(updateRequest.department());

    }

}
