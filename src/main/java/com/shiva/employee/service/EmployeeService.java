package com.shiva.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shiva.employee.dto.CreateEmployeeRequest;
import com.shiva.employee.dto.UpdateEmployeeRequest;
import com.shiva.employee.exception.DepartmentNotFoundException;
import com.shiva.employee.exception.EmployeeNotFoundException;
import com.shiva.employee.model.Department;
import com.shiva.employee.model.Employee;
import com.shiva.employee.repository.DepartmentRepository;
import com.shiva.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public void addEmployee(CreateEmployeeRequest createRequest) {
        Department department = this.departmentRepository.findByName(createRequest.department())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not exist"));
        Employee employee = new Employee(createRequest.name(), department, createRequest.salary());
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
        Employee emp = this.employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("Employee not found");
        });
        this.employeeRepository.delete(emp);
    }

    public void updateEmployee(Long id, UpdateEmployeeRequest updateRequest) {

        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("Employee not found");
        });
        Department department = this.departmentRepository.findByName(updateRequest.department())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not exist"));
        employee.setName(updateRequest.name());
        employee.setDepartment(department);
        employee.setSalary(updateRequest.salary());

        this.employeeRepository.save(employee);
    }

    public List<Employee> getByName(String name) {
        return this.employeeRepository.findByName(name);
    }

    public List<Employee> getByDepartment(String department) {
        return this.employeeRepository.findByDepartment_Name(department);
    }

    public List<Employee> getByNameAndDepartment(String name, String department) {
        System.err.println("--->>>" + department);
        return this.employeeRepository.findByNameAndDepartment_Name(name, department);
    }

    public List<Employee> getByNameContains(String name) {
        return this.employeeRepository.findByNameContaining(name);
    }

    public List<Employee> getBySalaryGreaterThan(Long salary) {
        return this.employeeRepository.findBySalaryGreaterThan(salary);
    }

    public List<Employee> getByDepartmentOrderBySalaryDesc(String department) {
        return this.employeeRepository.findByDepartment_NameOrderBySalaryDesc(department);
    }

}
