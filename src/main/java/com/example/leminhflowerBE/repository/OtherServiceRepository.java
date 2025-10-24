package com.example.leminhflowerBE.repository;

import com.example.leminhflowerBE.model.OtherService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtherServiceRepository extends JpaRepository<OtherService, Long> {

    List<OtherService> findByTypeIgnoreCase(String type);

    List<OtherService> findByTitleIgnoreCase(String title);

    List<OtherService> findByTitleContainingIgnoreCase(String keyword);

    List<OtherService> findByDescriptionContainingIgnoreCase(String keyword);
}
