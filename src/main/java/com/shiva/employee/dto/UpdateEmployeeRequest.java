package com.shiva.employee.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateEmployeeRequest(
                @NotBlank String name,
                @NotBlank String department) {
}
