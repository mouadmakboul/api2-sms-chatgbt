package com.example.demo.controllers;

import com.example.demo.Converter.CategoryConverter;
import com.example.demo.Entities.CategoryEntity.CategoryDto;
import com.example.demo.Entities.CategoryEntity.CategoryEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Exceptions.CategoryException;
import com.example.demo.Exceptions.LogementException;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.LogementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories") //matan ha ana anabghi njarab had l controller f category
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryConverter categoryConverter) {
        this.categoryService = categoryService;
        this.categoryConverter = categoryConverter;
    }
    @Autowired
    private LogementService logementservice;

    @GetMapping("/byName")
    public ResponseEntity<?> getCategoryByName(@RequestParam String name) {
        try {
            CategoryEntity category = categoryService.findByName(name);
            if (category == null) {
                throw new CategoryException("La catégorie avec le nom '" + name + "' est introuvable. Réessayez ultérieurement.");
            }

            CategoryDto categoryDto = categoryConverter.entityToDTO(category);
            return ResponseEntity.ok(categoryDto);
        } catch (CategoryException ex) {
            // Gérez explicitement l'exception CategoryException ici
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            // Gérez d'autres exceptions ici, si nécessaire
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne du serveur s'est produite.");
        }
    }



    @GetMapping("/byLogement")
    public ResponseEntity<?> getCategoriesByLogement(@RequestParam long logementId) {
        try {
            Optional<LogementEntity> logement = logementservice.findById(logementId);
            if (logement.isEmpty()) {
                throw new LogementException("Le logement avec l'ID " + logementId + " n'a pas été trouvé.");
            }

            List<CategoryEntity> categories = categoryService.findAllByLogementsContains(Optional.of(logement.get()));
            if (categories.isEmpty()) {
                throw new CategoryException("Aucune catégorie n'a été trouvée pour ce logement.");
            }

            List<CategoryDto> categoryDtos = categories.stream()
                    .map(categoryConverter::entityToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(categoryDtos);
        } catch (LogementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (CategoryException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur interne du serveur s'est produite.");
        }
    }


    @GetMapping("/existsByName")
    public ResponseEntity<?> doesCategoryExistByName(@RequestParam String name) {
        try {
            boolean exists = categoryService.existsByName(name);
            if (!exists) {
                throw new CategoryException("Aucune catégorie n'a été trouvée avec le nom spécifié : " + name);
            }
            return ResponseEntity.ok(exists);
        } catch (CategoryException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la vérification de l'existence de la catégorie.");
        }
    } }



