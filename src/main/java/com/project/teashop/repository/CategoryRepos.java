package com.project.teashop.repository;

import com.project.teashop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepos extends JpaRepository<Category, Long> {
}
