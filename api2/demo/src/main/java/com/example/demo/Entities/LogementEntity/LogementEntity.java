package com.example.demo.Entities.LogementEntity;


import com.example.demo.Entities.CategoryEntity.CategoryEntity;
import com.example.demo.Entities.EmplacementEntity.EmplacementEntity;
import com.example.demo.Entities.EquipementEntity.EquipementEntity;
import com.example.demo.Entities.ImageEntity.ImageEntity;
import com.example.demo.Entities.RatingEntity.RatingEntity;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "logements")
public class LogementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logement_id", nullable = false)
    private long id;
    private String title;
    private String description;
    private String adresse;
    private Date startdate;
    private Date enddate;
    @OneToMany(mappedBy = "logement" ,fetch = FetchType.LAZY)
    private List<ImageEntity> images;
    @OneToOne
    private RatingEntity rating;
    @OneToOne
    private UserEntity user; //hana jatni one to many
    @OneToMany(mappedBy = "logement" ,fetch = FetchType.LAZY)
    private List<ReservationEntity> reservations;
    @OneToMany(mappedBy = "logement" ,fetch = FetchType.LAZY)
    private List<EquipementEntity> equipements;
    @OneToOne
    private EmplacementEntity emplacement;
    @ManyToMany
    private List<CategoryEntity> categories;

}
