package com.example.demo.Service;

import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import com.example.demo.Exceptions.ReservationException;
import com.example.demo.Repositories.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepo reservationRepo;

    @Autowired
    public ReservationServiceImpl(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    @Override
    public List<ReservationEntity> findAllByLogement(Optional<LogementEntity> logement) {
        return reservationRepo.findAllByLogement(logement);
    }

    @Override
    public List<ReservationEntity> findAllByUserAndLogement(UserEntity user, LogementEntity logement) {
        return reservationRepo.findAllByUserAndLogement(user, logement);
    }

    @Override
    public ReservationEntity save(ReservationEntity reservation) {

        return reservationRepo.save(reservation);
    }




    @Override
    public void deleteById(Long id) {
        reservationRepo.deleteById(id);

    }

    @Override
    public List<ReservationEntity> findAllByStartDateBetween(Date startDate, Date endDate) {
        return reservationRepo.findAllByStartDateBetween(startDate, endDate);
    }
    @Override
    public boolean isReservationValid(ReservationEntity reservation) {
        if (reservation.getDatereservation() == null || reservation.getDatereservation().before(new Date())) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<ReservationEntity> findById(long reservationId) {
        return reservationRepo.findById(reservationId);
    }


}

