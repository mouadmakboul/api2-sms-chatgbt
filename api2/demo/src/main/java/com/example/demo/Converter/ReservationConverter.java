package com.example.demo.Converter;

import com.example.demo.Entities.ReservationEntity.ReservationDto;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationConverter {

    public ReservationDto entityToDTO(ReservationEntity reservationEntity) {
        return new ReservationDto() {{
            setId(reservationEntity.getId());
            setDatereservation(reservationEntity.getDatereservation());
            setStartDate(reservationEntity.getStartDate());
            setEndDate(reservationEntity.getEndtDate());
            // Vous pouvez continuer à définir d'autres propriétés ici
        }};
    }
}

