package com.example.demo.controllers;

import com.example.demo.Converter.EmplacementConverter;
import com.example.demo.Entities.EmplacementEntity.EmplacementDto;
import com.example.demo.Entities.EmplacementEntity.EmplacementEntity;
import com.example.demo.Entities.LogementEntity.LogementEntity;
import com.example.demo.Service.EmplacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emplacements")
public class EmplacementController {

    private final EmplacementService emplacementService;
    private final EmplacementConverter emplacementConverter; // Injectez EmplacementConverter

    @Autowired
    public EmplacementController(EmplacementService emplacementService, EmplacementConverter emplacementConverter) {
        this.emplacementService = emplacementService;
        this.emplacementConverter = emplacementConverter;
    }

    @GetMapping("/byZonegeo")
    public EmplacementDto getEmplacementByZonegeo(@RequestParam String zonegeo) {
        EmplacementEntity emplacementEntity = emplacementService.findByZonegeo(zonegeo);


        return emplacementConverter.entityToDTO(emplacementEntity);
    }

    @GetMapping("/logementsByZonegeo")
    public List<LogementEntity> getLogementsByZonegeo(@RequestParam String zonegeo) {
        return emplacementService.findLogementsByZonegeo(zonegeo);
    }

    @GetMapping("/existsById")
    public boolean doesEmplacementExistById(@RequestParam Long id) {
        return emplacementService.existsById(id);
    }

    @GetMapping("/countByZonegeo")
    public Long countEmplacementsByZonegeo(@RequestParam String zonegeo) {
        return emplacementService.countByZonegeo(zonegeo);
    }
}
