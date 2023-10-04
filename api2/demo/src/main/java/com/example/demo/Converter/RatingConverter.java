package com.example.demo.Converter;

import com.example.demo.Entities.RatingEntity.RatingDto;
import com.example.demo.Entities.RatingEntity.RatingEntity;
import org.springframework.stereotype.Component;

@Component
public class RatingConverter {

    public RatingDto entityToDTO(RatingEntity ratingEntity) {
        return new RatingDto() {{
            setId(ratingEntity.getId());
            setRating(ratingEntity.getRating());
            setRatingvalue(ratingEntity.getRatingvalue());
            // Ajoutez d'autres propriétés de l'entité RatingEntity ici
        }};
    }
}

