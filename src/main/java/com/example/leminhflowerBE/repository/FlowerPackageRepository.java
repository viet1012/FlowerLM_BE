package com.example.leminhflowerBE.repository;


import com.example.leminhflowerBE.model.FlowerPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerPackageRepository extends JpaRepository<FlowerPackage, Long> {
}
