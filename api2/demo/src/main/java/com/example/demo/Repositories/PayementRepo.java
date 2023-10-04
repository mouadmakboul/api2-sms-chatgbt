package com.example.demo.Repositories;

import com.example.demo.Entities.PayementEntity.PayementEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PayementRepo extends JpaRepository<PayementEntity,Long> {
    List<PayementEntity> findAllByUser(UserEntity user);
    List<PayementEntity> findAllByMethodepayement(String methodepayement);



}
