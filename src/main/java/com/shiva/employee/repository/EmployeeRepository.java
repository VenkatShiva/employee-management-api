package com.shiva.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiva.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
