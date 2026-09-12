package com.shiva.employee.dto;

import java.util.List;

public record EmployeeSkillSummaryResponse(Long id, String name, List<String> skill) {
}
