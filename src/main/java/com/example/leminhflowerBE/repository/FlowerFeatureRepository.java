package com.example.leminhflowerBE.repository;


import com.example.leminhflowerBE.model.FlowerFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerFeatureRepository extends JpaRepository<FlowerFeature, Long> {
    List<FlowerFeature> findAllByActiveTrueOrderByOrderIndexAsc();
}
