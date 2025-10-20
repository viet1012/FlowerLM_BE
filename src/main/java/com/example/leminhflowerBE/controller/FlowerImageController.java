package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.service.FlowerImageService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class FlowerImageController {

    private final FlowerImageService service;

    public FlowerImageController(FlowerImageService service) {
        this.service = service;
    }

    @GetMapping
    public List<FlowerImageDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FlowerImageDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public FlowerImageDTO create(@RequestBody FlowerImageDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public FlowerImageDTO update(@PathVariable Long id, @RequestBody FlowerImageDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
