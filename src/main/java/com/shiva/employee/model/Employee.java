package com.shiva.employee.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills = new ArrayList<>();

    @NotNull
    private Long salary;

    @ElementCollection
    @CollectionTable(name = "employee_projects", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "project_name")
    private List<String> projects = new ArrayList<>();

    protected Employee() {
    }

    public Employee(String name, Department department, Long salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Long getSalary() {
        return this.salary;
    }

    public void addSkill(Skill skill) {
        this.skills.add(skill);
    }

    public List<Skill> getSkills() {
        return this.skills.stream().toList();
    }

    public void addProject(String project) {
        this.projects.add(project);
    }

    public List<String> getProjects() {
        return this.projects.stream().toList();
    }

}
