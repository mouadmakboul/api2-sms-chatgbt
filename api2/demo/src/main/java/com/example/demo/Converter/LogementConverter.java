package com.example.demo.Converter;

import com.example.demo.Entities.LogementEntity.LogementDto;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import org.springframework.stereotype.Component;

@Component
public class LogementConverter {

    public LogementDto entityToDTO(LogementEntity logementEntity) {
        return new LogementDto() {{
            setId(logementEntity.getId());
            setTitle(logementEntity.getTitle());
            setDescription(logementEntity.getDescription());
            setAdresse(logementEntity.getAdresse());
            setStartdate(logementEntity.getStartdate());
            setEnddate(logementEntity.getEnddate());
            // Vous pouvez continuer à définir d'autres propriétés ici
        }};
    }
}

