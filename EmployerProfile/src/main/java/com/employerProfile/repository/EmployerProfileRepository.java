package com.employerProfile.repository;

import com.employerProfile.dto.EmployerProfileDTO;
import com.employerProfile.entity.EmployerProfile;
import com.employerProfile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, Integer> {
    EmployerProfile findByUser(User user);
}
