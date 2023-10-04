package com.example.demo.Service;


import com.example.demo.Entities.EmplacementEntity.EmplacementEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;

import java.util.List;

public interface EmplacementService {
    EmplacementEntity findByZonegeo(String zonegeo);
    List<LogementEntity> findLogementsByZonegeo(String zonegeo);
    boolean existsById(Long id);
    Long countByZonegeo(String zonegeo);
}

