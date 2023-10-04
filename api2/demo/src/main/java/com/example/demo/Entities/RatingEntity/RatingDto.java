package com.example.demo.Entities.RatingEntity;

import com.example.demo.Entities.LogementEntity.LogementDto;
import com.example.demo.Entities.UserEntity.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RatingDto {
    private long id;
    private String rating;
    private int ratingvalue;
    private LogementDto logement; // Utilisez un DTO pour le logement associé
    private UserDto user; // Utilisez un DTO pour l'utilisateur associé
}
