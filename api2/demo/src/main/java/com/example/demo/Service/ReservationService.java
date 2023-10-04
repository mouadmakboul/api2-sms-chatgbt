package com.example.demo.Service;


import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    List<ReservationEntity> findAllByLogement(Optional<LogementEntity> logement);
    List<ReservationEntity> findAllByUserAndLogement(UserEntity user, LogementEntity logement);
    ReservationEntity save(ReservationEntity reservation);

    // Ajouter une méthode delete personnalisée en utilisant l'ID
    void deleteById(Long id);
    List<ReservationEntity> findAllByStartDateBetween(Date startDate, Date endDate);

    boolean isReservationValid(ReservationEntity reservation);

    Optional<ReservationEntity> findById(long reservationId);
}

