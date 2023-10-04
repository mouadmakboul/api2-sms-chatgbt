package com.example.demo.Service;


import com.example.demo.Entities.PayementEntity.PayementEntity;
import com.example.demo.Entities.UserEntity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PayementService {
    List<PayementEntity> findAllByUser(UserEntity user);
    List<PayementEntity> findAllByMethodepayement(String methodepayement);
    Optional<PayementEntity> findById(Long id); // Ajout de la méthode findById

    PayementEntity save(PayementEntity payement); // Ajout de la méthode save

    void deleteById(Long id);
}
