package com.project.teashop.repository;

import com.project.teashop.entity.Tea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeaRepos extends JpaRepository<Tea, Long> {
}
