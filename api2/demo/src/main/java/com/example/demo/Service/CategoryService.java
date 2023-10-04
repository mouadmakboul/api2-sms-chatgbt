package com.example.demo.Service;


import com.example.demo.Entities.CategoryEntity.CategoryEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryEntity findByName(String name);
    List<CategoryEntity> findAllByLogementsContains(Optional<LogementEntity> logement);
    boolean existsByName(String name);
}

