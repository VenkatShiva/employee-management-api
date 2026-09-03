package com.shiva.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shiva.employee.dto.CreateEmployeeRequest;
import com.shiva.employee.dto.EmployeeResponse;
import com.shiva.employee.dto.UpdateEmployeeRequest;
import com.shiva.employee.exception.DepartmentNotFoundException;
import com.shiva.employee.exception.EmployeeNotFoundException;
import com.shiva.employee.exception.SkillAlreadyExistException;
import com.shiva.employee.exception.SkillNotFoundException;
import com.shiva.employee.model.Department;
import com.shiva.employee.model.Employee;
import com.shiva.employee.model.Skill;
import com.shiva.employee.repository.DepartmentRepository;
import com.shiva.employee.repository.EmployeeRepository;
import com.shiva.employee.repository.SkillRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final SkillRepository skillRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
            SkillRepository skillRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.skillRepository = skillRepository;
    }

    private static EmployeeResponse convertEmpToResponse(Employee emp) {
        return new EmployeeResponse(emp.getId(), emp.getName(), emp.getSalary());
    }

    private static List<EmployeeResponse> convertEmployeesToResponses(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeService::convertEmpToResponse)
                .toList();
    }

    public void addEmployee(CreateEmployeeRequest createRequest) {
        Department department = this.departmentRepository.findByName(createRequest.department())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not exist"));
        Employee employee = new Employee(createRequest.name(), department, createRequest.salary());
        this.employeeRepository.save(employee);
    }

    public List<EmployeeResponse> getAllEmployees() {
        return convertEmployeesToResponses(this.employeeRepository.findAll());
    }

    public EmployeeResponse getEmployee(Long id) {
        Employee emp = this.employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("Employee not found");
        });
        return convertEmpToResponse(emp);
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

    public List<EmployeeResponse> getByName(String name) {
        return convertEmployeesToResponses(this.employeeRepository.findByName(name));
    }

    public List<EmployeeResponse> getByDepartment(String department) {
        return convertEmployeesToResponses(this.employeeRepository.findByDepartment_Name(department));
    }

    public List<EmployeeResponse> getByNameAndDepartment(String name, String department) {
        return convertEmployeesToResponses(this.employeeRepository.findByNameAndDepartment_Name(name, department));
    }

    public List<EmployeeResponse> getByNameContains(String name) {
        return convertEmployeesToResponses(this.employeeRepository.findByNameContaining(name));
    }

    public List<EmployeeResponse> getBySalaryGreaterThan(Long salary) {
        return convertEmployeesToResponses(this.employeeRepository.findBySalaryGreaterThan(salary));
    }

    public List<EmployeeResponse> getByDepartmentOrderBySalaryDesc(String department) {
        return convertEmployeesToResponses(this.employeeRepository.findByDepartment_NameOrderBySalaryDesc(department));
    }

    @Transactional
    public void addSkill(Long id, String skillName) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("Employee not found");
        });

        List<Skill> allSkills = employee.getSkills();

        Optional<Skill> firstSkill = allSkills.stream()
                .filter((Skill skl) -> skl.getName().equals(skillName))
                .findFirst();
        if (firstSkill.isPresent()) {
            throw new SkillAlreadyExistException("Skill already exists");
        }
        Skill skill = this.skillRepository.findByName(skillName)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found"));

        employee.addSkill(skill);

        this.employeeRepository.save(employee);
    }

    public List<EmployeeResponse> getBySkill(String skillName) {
        return convertEmployeesToResponses(this.employeeRepository.findBySkills_Name(skillName));
    }

}
