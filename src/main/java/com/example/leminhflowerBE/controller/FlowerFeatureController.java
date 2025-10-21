package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.model.FlowerFeature;
import com.example.leminhflowerBE.service.FlowerFeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flower-features")
@CrossOrigin(origins = "*")
public class FlowerFeatureController {

    @Autowired
    private FlowerFeatureService service;

    @GetMapping
    public ResponseEntity<List<FlowerFeature>> getAll() {
        return ResponseEntity.ok(service.getAllFeatures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlowerFeature> getById(@PathVariable Long id) {
        return service.getFeatureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<List<FlowerFeature>> createFeatures(@RequestBody List<FlowerFeature> features) {
        List<FlowerFeature> created = service.createFeatures(features);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlowerFeature> update(@PathVariable Long id, @RequestBody FlowerFeature feature) {
        return ResponseEntity.ok(service.updateFeature(id, feature));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }
}
