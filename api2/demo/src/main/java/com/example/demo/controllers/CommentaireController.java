package com.example.demo.controllers;

import com.example.demo.Converter.CommentaireConverter;
import com.example.demo.Entities.CommentaireEntity.CommentaireDto;
import com.example.demo.Entities.CommentaireEntity.CommentaireEntity;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserEntity;
import com.example.demo.Exceptions.CommentaireException;
import com.example.demo.Exceptions.ReservationException;
import com.example.demo.Service.CommentaireService;
import com.example.demo.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;

    @Autowired
    public CommentaireController(CommentaireService commentaireService, CommentaireConverter commentaireConverter) {
        this.commentaireService = commentaireService;
        this.commentaireConverter = commentaireConverter;
    }
    @Autowired
    ReservationService reservationservice;
    private final CommentaireConverter commentaireConverter; // Injectez CommentaireConverter

    @GetMapping("/all")
    public ResponseEntity<?> getAllCommentaires() {
        try {
            List<CommentaireEntity> commentaires = commentaireService.findAll();

            // Convertissez la liste d'entités CommentaireEntity en une liste de DTO CommentaireDto en utilisant CommentaireConverter
            List<CommentaireDto> commentaireDtos = commentaires.stream()
                    .map(commentaireConverter::entityToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(commentaireDtos);
        } catch (CommentaireException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des commentaires.");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentaireById(@PathVariable long id) {
        try {
            commentaireService.deleteById(id);
            return ResponseEntity.ok("Commentaire supprimé avec succès.");
        }  catch (CommentaireException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression du commentaire.");
        }
    }

    @GetMapping("/byReservation")
    public ResponseEntity<?> getCommentairesByReservation(@RequestParam long reservationId) {
        try {
            Optional<ReservationEntity> reservation = reservationservice.findById(reservationId);
            if (reservation.isEmpty()) {
                throw new ReservationException("La réservation avec l'ID " + reservationId + " n'a pas été trouvée.");
            }

            List<CommentaireEntity> commentaires = commentaireService.findAllByReservation(Optional.of(reservation.get()));
            return ResponseEntity.ok(commentaires);
        } catch (ReservationException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des commentaires.");
        }
    }


    @GetMapping("/byUser")
    public List<CommentaireEntity> getCommentairesByUser(@RequestParam long userId) {
        UserEntity user = new UserEntity(); // Vous devrez obtenir l'utilisateur à partir de votre service d'utilisateur

        // Exemple de lancement d'une exception personnalisée
        if (user == null) {
            throw new CommentaireException("L'utilisateur n'a pas été trouvé.");
        }

        return commentaireService.findAllByUser(user);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCommentaire(@RequestBody CommentaireEntity commentaire) {
        try {
            CommentaireEntity createdCommentaire = commentaireService.save(commentaire);

            // Convertissez l'entité CommentaireEntity créée en DTO CommentaireDto en utilisant CommentaireConverter
            CommentaireDto createdCommentaireDto = commentaireConverter.entityToDTO(createdCommentaire);

            return ResponseEntity.ok(createdCommentaireDto);
        } catch (CommentaireException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création du commentaire.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentaireById(@PathVariable long id) {
        try {
            CommentaireEntity commentaire = commentaireService.findById(id).orElse(null);
            if (commentaire == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commentaire non trouvé.");
            }
            return ResponseEntity.ok(commentaire);
        } catch (CommentaireException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération du commentaire.");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommentaire(@PathVariable long id, @RequestBody CommentaireEntity updatedCommentaire) {
        try {

            CommentaireEntity savedCommentaire = commentaireService.save(updatedCommentaire);


            CommentaireDto savedCommentaireDto = commentaireConverter.entityToDTO(savedCommentaire);

            return ResponseEntity.ok(savedCommentaireDto);
        }  catch (CommentaireException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du commentaire.");
        }
    }
}







