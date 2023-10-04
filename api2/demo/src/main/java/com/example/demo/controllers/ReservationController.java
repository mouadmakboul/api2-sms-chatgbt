package com.example.demo.controllers;

import com.example.demo.Converter.ReservationConverter;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Entities.ReservationEntity.ReservationDto;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import com.example.demo.Exceptions.ReservationException;
import com.example.demo.Service.LogementService;
import com.example.demo.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationConverter reservationConverter; // Injectez ReservationConverter

    @Autowired
    public ReservationController(ReservationService reservationService, ReservationConverter reservationConverter) {
        this.reservationService = reservationService;
        this.reservationConverter = reservationConverter;
    }
    @Autowired
    LogementService logementService;

    @GetMapping("/byLogement")
    public ResponseEntity<?> getReservationsByLogement(@RequestParam long logementId) {
        try {
            Optional<LogementEntity> logement = logementService.findById(logementId); // Obtenez le logement à partir de votre service de logement
            if (logement.isPresent()) {
                List<ReservationEntity> reservations = reservationService.findAllByLogement(Optional.of(logement.get()));

                // Utilisez ReservationConverter pour convertir les entités en DTOs
                List<ReservationDto> reservationDtos = reservations.stream()
                        .map(reservationConverter::entityToDTO)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(reservationDtos);
            } else {
                // Personnalisez le message d'erreur ici
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Logement not found with ID: " + logementId);
            }
        } catch (Exception ex) {
            // Lancer une exception personnalisée avec un message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de la récupération des réservations : " + ex.getMessage());
        }
    }




    @GetMapping("/byUserAndLogement")
    public ResponseEntity<?> getReservationsByUserAndLogement(
            @RequestBody UserEntity user,
            @RequestBody LogementEntity logement) {

        try {
            List<ReservationEntity> reservations = reservationService.findAllByUserAndLogement(user, logement);


            List<ReservationDto> reservationDtos = reservations.stream()
                    .map(reservationConverter::entityToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(reservationDtos);
        } catch (ReservationException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationEntity reservation) {
        try {
            // Vérifiez si les dates de début et de fin sont valides
            LocalDateTime startDate = reservation.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endDate = reservation.getEndtDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            if (startDate.isAfter(endDate)) {
                throw new ReservationException("La date de début ne peut pas être après la date de fin.");
            }

            // Enregistrez la réservation
            ReservationEntity createdReservation = reservationService.save(reservation);

            ReservationDto reservationDto = reservationConverter.entityToDTO(createdReservation);

            return ResponseEntity.status(HttpStatus.CREATED).body(reservationDto);
        } catch (ReservationException ex) {
            // En cas d'erreur, renvoyez simplement le message d'erreur personnalisé
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }






    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservationById(@PathVariable Long id) {
        try {
            Optional<ReservationEntity> reservationOptional = reservationService.findById(id);

            if (!reservationOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La réservation avec l'ID spécifié n'a pas été trouvée.");
            }

            Optional<ReservationEntity> reservation = Optional.of(reservationOptional.get());
            reservationService.deleteById(id);
            return ResponseEntity.ok("La réservation avec l'ID " + id + " a été supprimée avec succès.");
        } catch (ReservationException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de la réservation.");
        }
    }




    @GetMapping("/byStartDateBetween")
    public List<ReservationDto> getReservationsByStartDateBetween(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<ReservationEntity> reservations = reservationService.findAllByStartDateBetween(startDate, endDate);


        List<ReservationDto> reservationDtos = reservations.stream()
                .map(reservationConverter::entityToDTO)
                .collect(Collectors.toList());

        return reservationDtos;
    }
}
