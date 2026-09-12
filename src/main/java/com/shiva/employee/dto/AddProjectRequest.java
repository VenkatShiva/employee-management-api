package com.shiva.employee.dto;

import jakarta.validation.constraints.NotBlank;

public record AddProjectRequest(@NotBlank String name) {
}
