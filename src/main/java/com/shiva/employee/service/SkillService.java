package com.shiva.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shiva.employee.model.Skill;
import com.shiva.employee.repository.SkillRepository;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getSkills() {
        return this.skillRepository.findAll();
    }

}
