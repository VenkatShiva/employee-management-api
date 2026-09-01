package com.shiva.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiva.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    List<Employee> findByDepartment_Name(String department);

    List<Employee> findByNameAndDepartment_Name(String name, String department);

    List<Employee> findByNameContaining(String name);

    List<Employee> findBySalaryGreaterThan(Long salary);

    List<Employee> findByDepartment_NameOrderBySalaryDesc(String department);

    List<Employee> findBySkills_Name(String name);

}
