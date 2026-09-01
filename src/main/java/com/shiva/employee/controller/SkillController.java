package com.shiva.employee.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiva.employee.model.Skill;
import com.shiva.employee.service.SkillService;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("")
    ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(this.skillService.getSkills());
    }
}
