package com.example.demo.Service;

import com.example.demo.Entities.EquipementEntity.EquipementEntity;
import com.example.demo.Exceptions.EquipementException;
import com.example.demo.Repositories.EquipementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipementServiceImpl implements EquipementService {
    private final EquipementRepo equipementRepo;

    @Autowired
    public EquipementServiceImpl(EquipementRepo equipementRepo) {
        this.equipementRepo = equipementRepo;
    }

    @Override
    public EquipementEntity findByNameEquipement(String nameEquipement) {
        EquipementEntity equipement = equipementRepo.findByNameEquipement(nameEquipement);
        if (equipement == null) {
            throw new EquipementException("L'équipement spécifié n'a pas été trouvé.");
        }
        return equipement;
    }

    @Override
    public boolean existsByNameEquipement(String nameEquipement) {
        return equipementRepo.existsByNameEquipement(nameEquipement);
    }
}

