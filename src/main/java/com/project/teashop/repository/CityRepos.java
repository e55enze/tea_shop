package com.project.teashop.repository;

import com.project.teashop.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepos extends JpaRepository<City, Long> {
}
