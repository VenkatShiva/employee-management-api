package com.shiva.employee.dto;

import java.util.List;

public record EmployeeSkillProjectSummaryResponse(Long id, String name, List<String> skills, List<String> projects) {
}
