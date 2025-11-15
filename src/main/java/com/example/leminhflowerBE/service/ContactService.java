package com.example.leminhflowerBE.service;
import com.example.leminhflowerBE.dto.ContactDTO;
import com.example.leminhflowerBE.model.Contact;
import com.example.leminhflowerBE.repository.ContactRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContactService {

    private final ContactRepository repository;

    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> findAll() {
        return repository.findAll();
    }

    public Contact findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Contact create(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setAddress(dto.getAddress());
        contact.setLinkAddress(dto.getLinkAddress());
        contact.setContact1(dto.getContact1());
        contact.setContact2(dto.getContact2());
        contact.setEmail(dto.getEmail());
        return repository.save(contact);
    }

    public Contact update(Long id, ContactDTO dto) {
        Contact existing = repository.findById(id).orElse(null);
        if (existing == null) return null;

        // 🔥 PARTIAL UPDATE — chỉ update field có gửi lên
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        if (dto.getLinkAddress() != null) existing.setLinkAddress(dto.getLinkAddress());
        if (dto.getContact1() != null) existing.setContact1(dto.getContact1());
        if (dto.getContact2() != null) existing.setContact2(dto.getContact2());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());

        return repository.save(existing);
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
