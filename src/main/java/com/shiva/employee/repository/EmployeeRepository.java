package com.shiva.employee.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shiva.employee.exception.EmployeeAlreadyExistException;
import com.shiva.employee.exception.EmployeeNotFoundException;
import com.shiva.employee.model.Employee;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees;

    public EmployeeRepository() {
        this.employees = new ArrayList<>();
    }

    public Optional<Employee> findById(Long id) {
        return employees
                .stream()
                .filter(emp -> Objects.equals(emp.getId(), id))
                .findAny();
    }

    public List<Employee> findAll() {
        return employees
                .stream()
                .toList();
    }

    public void save(Employee employee) {
        Optional<Employee> optionalEmp = findById(employee.getId());
        if (optionalEmp.isPresent()) {
            throw new EmployeeAlreadyExistException("Employee id already present");
        }
        this.employees.add(employee);
    }

    public void delete(Long id) {
        Optional<Employee> optionalEmp = findById(id);
        if (optionalEmp.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not present");
        }
        this.employees.removeIf(emp -> Objects.equals(emp.getId(), id));
    }

}
