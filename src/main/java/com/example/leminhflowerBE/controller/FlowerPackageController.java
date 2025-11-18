package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.FlowerPackageDTO;
import com.example.leminhflowerBE.model.FlowerPackage;
import com.example.leminhflowerBE.service.FlowerPackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flower-packages")
public class FlowerPackageController {

    private final FlowerPackageService flowerPackageService;

    public FlowerPackageController(FlowerPackageService flowerPackageService) {
        this.flowerPackageService = flowerPackageService;
    }

    // -------------------
    // Create package
    // -------------------
    @PostMapping
    public ResponseEntity<FlowerPackageDTO> createPackage(@RequestBody FlowerPackage flowerPackage) {
        FlowerPackageDTO created = flowerPackageService.createFlowerPackage(flowerPackage);
        return ResponseEntity.ok(created);
    }

    // -------------------
    // Get all packages
    // -------------------
    @GetMapping
    public ResponseEntity<List<FlowerPackageDTO>> getAllPackages() {
        return ResponseEntity.ok(flowerPackageService.getAllFlowerPackages());
    }

    // -------------------
    // Get a package by ID
    // -------------------
    @GetMapping("/{id}")
    public ResponseEntity<FlowerPackageDTO> getPackage(@PathVariable Long id) {
        try {
            FlowerPackageDTO dto = flowerPackageService.getFlowerPackageById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // -------------------
    // Add flower into package
    // -------------------
    @PostMapping("/{packageId}/flowers/{flowerId}")
    public ResponseEntity<FlowerPackageDTO> addFlowerToPackage(
            @PathVariable Long packageId,
            @PathVariable Long flowerId) {
        try {
            FlowerPackageDTO updatedPackage =
                    flowerPackageService.addFlowerToPackage(packageId, flowerId);
            return ResponseEntity.ok(updatedPackage);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // -------------------
    // Remove flower from package
    // -------------------
    @DeleteMapping("/{packageId}/flowers/{flowerId}")
    public ResponseEntity<FlowerPackageDTO> removeFlowerFromPackage(
            @PathVariable Long packageId,
            @PathVariable Long flowerId) {
        try {
            FlowerPackageDTO updatedPackage =
                    flowerPackageService.removeFlowerFromPackage(packageId, flowerId);
            return ResponseEntity.ok(updatedPackage);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
