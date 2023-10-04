
package com.example.demo.Service;

import com.example.demo.Entities.CategoryEntity.CategoryEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Exceptions.CategoryException;
import com.example.demo.Repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public CategoryEntity findByName(String name) {
        CategoryEntity category = categoryRepo.findByName(name);
        if (category == null) {
            throw new CategoryException("La catégorie spécifiée n'a pas été trouvée.");
        }
        return category;
    }


    @Override
    public List<CategoryEntity> findAllByLogementsContains(Optional<LogementEntity> logement) {
        return categoryRepo.findAllByLogementsContains(logement);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepo.existsByName(name);
    }
}
