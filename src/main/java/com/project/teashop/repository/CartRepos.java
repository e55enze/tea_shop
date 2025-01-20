package com.project.teashop.repository;

import com.project.teashop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepos extends JpaRepository<Cart, Long> {
}
