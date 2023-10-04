package com.example.demo.Service;

import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Entities.RatingEntity.RatingEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import com.example.demo.Exceptions.RatingException;
import com.example.demo.Repositories.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepo ratingRepo;

    @Autowired
    public RatingServiceImpl(RatingRepo ratingRepo) {
        this.ratingRepo = ratingRepo;
    }

    @Override
    public List<RatingEntity> findAllByLogement(LogementEntity logement) {
        return ratingRepo.findAllByLogement(logement);
    }

    @Override
    public List<RatingEntity> findAllByUser(UserEntity user) {
        return ratingRepo.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        ratingRepo.deleteById(id);
    }

    @Override
    public RatingEntity save(RatingEntity rating) {
        if (rating.getRatingvalue() < 1 || rating.getRatingvalue() > 5) {
            throw new RatingException("La valeur du rating doit être comprise entre 1 et 5.");
        }
        return ratingRepo.save(rating);
    }

    @Override
    public Optional<RatingEntity> findById(Long id) {
        return ratingRepo.findById(id);
    }

}
