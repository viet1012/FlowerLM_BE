package com.example.leminhflowerBE.controller;
import com.example.leminhflowerBE.dto.FlowerDTO;
import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.request.FlowerRequest;
import com.example.leminhflowerBE.service.FlowerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/flowers")
public class FlowerController {

    private final FlowerService service;

    public FlowerController(FlowerService service) {
        this.service = service;
    }

    @GetMapping
    public List<FlowerDTO> getAll() { return service.getAll(); }

    // ✅ Lấy danh sách hoa theo group (category)
    @GetMapping("/group/{groupId}")
    public List<FlowerDTO> getByGroup(@PathVariable Long groupId) {
        return service.getByGroupId(groupId);
    }

    @GetMapping("/{id}")
    public FlowerDTO getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public FlowerDTO create(@RequestBody FlowerRequest flower) { return service.create(flower); }

    @PutMapping("/{id}")
    public FlowerDTO update(@PathVariable Long id, @RequestBody FlowerRequest flower) {
        return service.update(id, flower);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

}
