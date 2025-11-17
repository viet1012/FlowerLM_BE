package com.example.leminhflowerBE.repository;

import com.example.leminhflowerBE.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
