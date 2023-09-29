package com.user.repository;

import com.user.entity.User;
import com.user.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {
    UserProfile findByUser(User user);
}
