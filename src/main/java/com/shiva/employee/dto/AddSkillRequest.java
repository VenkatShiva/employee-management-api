package com.shiva.employee.dto;

import jakarta.validation.constraints.NotBlank;

public record AddSkillRequest(
        @NotBlank String name) {
}
