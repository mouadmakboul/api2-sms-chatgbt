package com.example.demo.controllers;

import com.example.demo.Converter.ReservationConverter;
import com.example.demo.Converter.UserConverter;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Entities.ReservationEntity.ReservationDto;
import com.example.demo.Entities.ReservationEntity.ReservationEntity;
import com.example.demo.Entities.UserEntity.UserDto;
import com.example.demo.Entities.UserEntity.UserEntity;
import com.example.demo.Exceptions.LogementException;
import com.example.demo.Exceptions.ReservationException;
import com.example.demo.Exceptions.UserException;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;
    private final ReservationConverter reservationConverter; // Injectez ReservationConverter// Injectez UserConverter

    @Autowired
    public UserController(UserService userService, UserConverter userConverter, ReservationConverter reservationConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.reservationConverter = reservationConverter;
    }




    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            Optional<UserEntity> user = userService.findByUsername(username);
            if (user.isPresent()) {
                // Utilisez UserConverter pour convertir l'entité en DTO
                UserDto userDto = userConverter.entityToDTO(user.get());
                return ResponseEntity.ok(userDto);
            } else {
                throw new UserException("Utilisateur non trouvé avec le nom d'utilisateur : " + username);
            }
        } catch (UserException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/existsByEmail/{email}")
    public Boolean existsByEmail(@PathVariable String email) {
        return userService.existsByEmail(email);
    }




    @GetMapping("/{userId}/logements")
    public ResponseEntity<?> getLogementsByUser(@PathVariable Long userId) {
        try {
            UserEntity user = userService.findById(userId);
            List<LogementEntity> logements = userService.getLogementsByUser(user);

            if (!logements.isEmpty()) {
                // Vous pouvez utiliser un LogementConverter pour convertir la liste d'entités en liste de DTO si nécessaire

                return ResponseEntity.ok(logements);
            } else {
                throw new LogementException("Aucun logement trouvé pour l'utilisateur avec l'ID : " + userId);
            }
        } catch (UserException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (LogementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des logements de l'utilisateur.");
        }
    }

    @GetMapping("/{userId}/reservations")
    public ResponseEntity<?> getReservationsByUser(@PathVariable Long userId) {
        try {
            UserEntity user = userService.findById(userId);
            List<ReservationEntity> reservations = userService.getReservationsByUser(user);

            if (!reservations.isEmpty()) {
                // Vous pouvez utiliser un ReservationConverter pour convertir la liste d'entités en liste de DTO si nécessaire
                List<ReservationDto> reservationDtos = reservations.stream()
                        .map(reservationConverter::entityToDTO)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(reservationDtos);
            } else {
                throw new ReservationException("Aucune réservation trouvée pour l'utilisateur avec l'ID : " + userId);
            }
        } catch (UserException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (ReservationException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des réservations de l'utilisateur.");
        }
    }
    @GetMapping("/users")
    public String getUsers(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // S'il y a des erreurs de validation, construisez un message d'erreur personnalisé
            StringBuilder errorMessage = new StringBuilder("Erreur de validation : ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }

            // Créez une réponse JSON avec le message d'erreur
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", errorMessage.toString());

            // Utilisez un code d'erreur approprié, par exemple, 400 Bad Request pour une requête incorrecte
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } else {
            // Vérifiez si l'email existe déjà dans la base de données
            if (userService.existsByEmail(user.getEmail())) {
                // L'email existe déjà, renvoyez un message d'erreur
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "L'adresse e-mail est déjà utilisée par un autre utilisateur.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Vérifiez si le nom d'utilisateur existe déjà dans la base de données
            if (userService.existsByUsername(user.getUsername())) {
                // Le nom d'utilisateur existe déjà, renvoyez un message d'erreur
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Le nom d'utilisateur est déjà utilisé par un autre utilisateur.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Vérifiez si le mot de passe existe déjà dans la base de données (c'est une vérification que vous devrez implémenter dans votre service)
            if (userService.existsByPassword(user.getPassword())) {
                // Le mot de passe existe déjà, renvoyez un message d'erreur
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Le mot de passe est déjà utilisé par un autre utilisateur.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Le corps de la requête est valide et les vérifications d'email, de nom d'utilisateur et de mot de passe sont réussies, continuez avec le traitement
            try {
                UserEntity savedUser = userService.saveUser(user);
                UserDto userDto = userConverter.entityToDTO(savedUser);
                return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
            } catch (UserException ex) {
                // Gérer d'autres erreurs ici
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("une erreur s'est produite lors de la création");
            }
        }


    }




    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("Utilisateur supprimé avec succès.");
        } catch (UserException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("L'utilisateur avec l'ID spécifié n'a pas été trouvé.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression de l'utilisateur.");
        }
    }



}
