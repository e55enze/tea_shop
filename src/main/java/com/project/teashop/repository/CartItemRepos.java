package com.project.teashop.repository;

import com.project.teashop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepos extends JpaRepository<CartItem, Long> {
}
