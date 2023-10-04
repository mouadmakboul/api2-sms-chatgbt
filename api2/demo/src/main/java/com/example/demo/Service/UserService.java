package com.example.demo.Service;


import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    List<LogementEntity> getLogementsByUser(UserEntity user);

    List<ReservationEntity> getReservationsByUser(UserEntity user);

    UserEntity findById(Long userId);

    UserEntity saveUser(UserEntity user);

    void deleteUser(Long userId);

    boolean existsByPassword(String password);
}
