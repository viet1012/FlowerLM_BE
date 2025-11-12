package com.example.leminhflowerBE.controller;// 📁 package com.example.leminhflowerBE.controller

import com.example.leminhflowerBE.dto.OtherServiceDTO;
import com.example.leminhflowerBE.service.OtherServiceService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/other-services")
@CrossOrigin(origins = "*")
public class OtherServiceController {

    private final OtherServiceService service;

    public OtherServiceController(OtherServiceService service) {
        this.service = service;
    }

    @GetMapping
    public List<OtherServiceDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public OtherServiceDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public OtherServiceDTO create(@RequestBody OtherServiceDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public OtherServiceDTO update(@PathVariable Long id, @RequestBody OtherServiceDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
