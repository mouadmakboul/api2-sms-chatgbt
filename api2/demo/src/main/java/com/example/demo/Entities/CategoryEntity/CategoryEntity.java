package com.example.demo.Entities.CategoryEntity;


import com.example.demo.Entities.LogementEntity.LogementEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private long id;
    private String name;
    private String image;
    @OneToMany
    private List<LogementEntity> logements;
}
