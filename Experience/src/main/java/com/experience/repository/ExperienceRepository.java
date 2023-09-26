package com.experience.repository;

import com.experience.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
}
