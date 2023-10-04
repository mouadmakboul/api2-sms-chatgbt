package com.example.demo.Entities.RatingEntity;


import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ratings")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Rating_id", nullable = false)
    private long id;
    private String rating;
    private int ratingvalue;
    @OneToOne
    private LogementEntity logement; //jatni hana 3la reservation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="listing_id", nullable = false)
    private LogementEntity listing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="user_id", nullable = false)
    private UserEntity user;
}
