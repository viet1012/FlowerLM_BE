package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.service.FlowerGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class FlowerGroupController {

    private final FlowerGroupService service;

    public FlowerGroupController(FlowerGroupService service) {
        this.service = service;
    }

    @GetMapping
    public List<FlowerGroup> getAll() {
        return service.getAll();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getGroupWithCount(id));
    }



    @GetMapping("/{id}")
    public FlowerGroup getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public FlowerGroup create(@RequestBody FlowerGroup group) {
        return service.create(group);
    }

    @PutMapping("/{id}")
    public FlowerGroup update(@PathVariable Long id, @RequestBody FlowerGroup group) {
        return service.update(id, group);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
