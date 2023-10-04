package com.example.demo.Repositories;

import com.example.demo.Entities.CommentaireEntity.CommentaireEntity;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentaireRepo extends JpaRepository<CommentaireEntity,Long> {


    List<CommentaireEntity> findAll();
    void deleteById(long id);
    List<CommentaireEntity> findAllByReservation(Optional<ReservationEntity> reservation);
    List<CommentaireEntity> findAllByUser(UserEntity user);



}
