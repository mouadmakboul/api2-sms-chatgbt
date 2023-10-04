package com.example.demo.Service;

import com.example.demo.Entities.EmplacementEntity.EmplacementEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Exceptions.EmplacementException;
import com.example.demo.Repositories.EmplacementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmplacementServiceImpl implements EmplacementService {
    private final EmplacementRepo emplacementRepo;

    @Autowired
    public EmplacementServiceImpl(EmplacementRepo emplacementRepo) {
        this.emplacementRepo = emplacementRepo;
    }

    @Override
    public EmplacementEntity findByZonegeo(String zonegeo) {
        EmplacementEntity emplacement = emplacementRepo.findByZonegeo(zonegeo);
        if (emplacement == null) {
            throw new EmplacementException("L'emplacement spécifié n'a pas été trouvé.");
        }
        return emplacement;
    }


    @Override
    public List<LogementEntity> findLogementsByZonegeo(String zonegeo) {
        return emplacementRepo.findLogementsByZonegeo(zonegeo);

    }

    @Override
    public boolean existsById(Long id) {
        return emplacementRepo.existsById(id);
    }

    @Override
    public Long countByZonegeo(String zonegeo) {
        return emplacementRepo.countByZonegeo(zonegeo);
    }
}

