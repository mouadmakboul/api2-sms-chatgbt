package com.example.demo.Service;


import com.example.demo.Entities.CommentaireEntity.CommentaireEntity;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CommentaireService {

    List<CommentaireEntity> findAll();

    void deleteById(long id);

    List<CommentaireEntity> findAllByReservation(Optional<ReservationEntity> reservation);

    List<CommentaireEntity> findAllByUser(UserEntity user);

    // Redéfinition de la méthode save

    CommentaireEntity save(CommentaireEntity reservation);

    Optional<CommentaireEntity> findById(long id);
}
