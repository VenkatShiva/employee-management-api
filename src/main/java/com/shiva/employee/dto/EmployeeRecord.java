package com.shiva.employee.dto;

import jakarta.validation.constraints.NotBlank;

public record EmployeeRecord(
        @NotBlank String name,
        @NotBlank String department) {
};
