package com.project.teashop.repository;

import com.project.teashop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User, Long> {
}
