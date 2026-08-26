package com.shiva.employee.repository;

import org.springframework.stereotype.Repository;

import com.shiva.employee.model.Employee;

@Repository
public class EmployeeRepository {

    public void save(Employee employee) {
        System.out.println("Employee details:");
        System.out.println("ID: " + employee.getId() + ", Name: " + employee.getName() + ", Department: " + employee.getDepartment());
    }
}
