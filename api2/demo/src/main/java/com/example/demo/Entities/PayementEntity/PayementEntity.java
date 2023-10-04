package com.example.demo.Entities.PayementEntity;

import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payements")
public class PayementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payement_id", nullable = false)
    private long id;
    private String methodepayement;
    private long Amount;
    @OneToOne
    private UserEntity user;
    @OneToOne
    private ReservationEntity reservation;
}
