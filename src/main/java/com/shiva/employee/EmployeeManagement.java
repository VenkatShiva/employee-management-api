package com.shiva.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.shiva.employee.model.Employee;
import com.shiva.employee.service.EmployeeService;

@SpringBootApplication
public class EmployeeManagement {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EmployeeManagement.class, args);
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        Employee employee = new Employee(1L, "Shiva", "Development");
        employeeService.addEmployee(employee);
    }
}
