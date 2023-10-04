package com.example.demo.Repositories;

import com.example.demo.Entities.EmplacementEntity.EmplacementEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmplacementRepo extends JpaRepository<EmplacementEntity,Long> {
    EmplacementEntity findByZonegeo(String zonegeo);
    List<LogementEntity> findLogementsByZonegeo(String zonegeo);
    boolean existsById(Long id);
    Long countByZonegeo(String zonegeo);



}
