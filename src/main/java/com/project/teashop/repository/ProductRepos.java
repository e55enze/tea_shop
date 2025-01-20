package com.project.teashop.repository;

import com.project.teashop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepos extends JpaRepository<Product, Long> {
}
