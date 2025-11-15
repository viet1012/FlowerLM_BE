package com.example.leminhflowerBE.controller;
import com.example.leminhflowerBE.dto.ContactDTO;
import com.example.leminhflowerBE.model.Contact;
import com.example.leminhflowerBE.service.ContactService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contact> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Contact getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Contact create(@RequestBody ContactDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}") // PATCH = update 1 phần
    public Contact update(@PathVariable Long id, @RequestBody ContactDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
