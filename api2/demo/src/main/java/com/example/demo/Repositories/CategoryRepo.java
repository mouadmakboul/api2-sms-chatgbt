package com.example.demo.Repositories;

import com.example.demo.Entities.CategoryEntity.CategoryEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity,Long> {
    CategoryEntity findByName(String name);
    List<CategoryEntity> findAllByLogementsContains(Optional<LogementEntity> logement);
    boolean existsByName(String name);

}
