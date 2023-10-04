package com.example.demo.Entities.EmplacementEntity;


import com.example.demo.Entities.LogementEntity.LogementDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Data
public class EmplacementDto {
    private long id;
    private String zonegeo;
    private LogementDto logement; // Utilisez un DTO pour le logement associé, par exemple, LogementDto
}
