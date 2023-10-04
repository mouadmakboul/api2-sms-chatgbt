package com.example.demo.Repositories;

import com.example.demo.Entities.EquipementEntity.EquipementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipementRepo extends JpaRepository<EquipementEntity,Long> {
    EquipementEntity findByNameEquipement(String nameEquipement);

    boolean existsByNameEquipement(String nameEquipement);



}
