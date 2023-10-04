package com.example.demo.Service;


import com.example.demo.Entities.EquipementEntity.EquipementEntity;

public interface EquipementService {
    EquipementEntity findByNameEquipement(String nameEquipement);
    boolean existsByNameEquipement(String nameEquipement);
}

