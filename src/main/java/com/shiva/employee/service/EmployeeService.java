package com.shiva.employee.service;

import java.util.List;
import java.util.Optional;

import com.shiva.employee.dto.*;
import com.shiva.employee.exception.*;
import org.springframework.stereotype.Service;

import com.shiva.employee.model.Department;
import com.shiva.employee.model.Employee;
import com.shiva.employee.model.Skill;
import com.shiva.employee.repository.DepartmentRepository;
import com.shiva.employee.repository.EmployeeRepository;
import com.shiva.employee.repository.SkillRepository;
import org.springframework.transaction.annotation.Transactional;


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
        Employee emp = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return convertEmpToResponse(emp);
    }

    public void deleteEmployee(Long id) {
        Employee emp = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        this.employeeRepository.delete(emp);
    }

    @Transactional
    public void updateEmployee(Long id, UpdateEmployeeRequest updateRequest) {

        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
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
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

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

    public List<EmployeeDetailsResponse> getEmployeeDetails() {
        List<Employee> employees = this.employeeRepository.findAllWithDepartment();
        return employees.stream().map(emp -> new EmployeeDetailsResponse(emp.getId(), emp.getName(), emp.getSalary(),
                emp.getDepartment().getName())
        ).toList();

    }

    public List<EmployeeSkillSummaryResponse> getEmployeesWithSkills() {
        List<Employee> employees = this.employeeRepository.findAllWithSkills();
        return employees.stream()
                .map(emp -> {
                    List<String> skills = emp.getSkills().stream().map(Skill::getName).toList();
                    return new EmployeeSkillSummaryResponse(emp.getId(), emp.getName(), skills);
                })
                .toList();
    }

    @Transactional
    public void addProject(Long id, String projectName) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employee.getProjects()
                .stream()
                .filter(prj -> prj.equalsIgnoreCase(projectName))
                .findFirst()
                .ifPresent(prj -> {
                    throw new ProjectAlreadyExistException("Project already exists");
                });

        employee.addProject(projectName);

        this.employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeSkillProjectSummaryResponse> getEmployeesWithSkillsAndProjects() {
        List<Employee> employees = this.employeeRepository.findAllWithSkills();
        this.employeeRepository.findAllWithProjects();
        return employees.stream()
                .map(emp -> {
                    List<String> skills = emp.getSkills().stream().map(Skill::getName).toList();
                    List<String> projects = emp.getProjects();
                    return new EmployeeSkillProjectSummaryResponse(emp.getId(), emp.getName(), skills, projects);
                })
                .toList();
    }

}
