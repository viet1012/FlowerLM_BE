package com.example.leminhflowerBE.controller;
import com.example.leminhflowerBE.model.FlowerImage;
import com.example.leminhflowerBE.repository.FlowerImageRepository;
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
    public List<FlowerImage> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public FlowerImage getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public FlowerImage create(@RequestBody FlowerImage image) { return service.create(image); }

    @PutMapping("/{id}")
    public FlowerImage update(@PathVariable Long id, @RequestBody FlowerImage image) {
        return service.update(id, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

}
