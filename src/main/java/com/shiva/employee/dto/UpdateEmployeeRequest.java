package com.shiva.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEmployeeRequest(
        @NotBlank String name,
        @NotBlank String department,
        @NotNull Long salary) {
}
