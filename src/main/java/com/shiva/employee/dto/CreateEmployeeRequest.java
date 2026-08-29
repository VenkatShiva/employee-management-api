
package com.shiva.employee.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEmployeeRequest(
                @NotBlank String name,
                @NotBlank String department) {
}
