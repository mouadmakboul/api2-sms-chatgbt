package com.example.demo.controllers;

import com.example.demo.Entities.EquipementEntity.EquipementEntity;
import com.example.demo.Service.EquipementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipements")
public class EquipementController {

    private final EquipementService equipementService;

    @Autowired
    public EquipementController(EquipementService equipementService) {
        this.equipementService = equipementService;
    }

    @GetMapping("/byName")
    public EquipementEntity getEquipementByName(@RequestParam String nameEquipement) {
        return equipementService.findByNameEquipement(nameEquipement);
    }

    @GetMapping("/existsByName")
    public boolean doesEquipementExistByName(@RequestParam String nameEquipement) {
        return equipementService.existsByNameEquipement(nameEquipement);
    }
}
