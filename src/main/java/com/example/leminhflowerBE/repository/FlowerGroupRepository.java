package com.example.leminhflowerBE.repository;


import com.example.leminhflowerBE.model.FlowerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlowerGroupRepository extends JpaRepository<FlowerGroup, Long> {
    @Query("SELECT COUNT(f) FROM Flower f WHERE f.group.groupId = :groupId")
    long countByGroup_GroupId(Long groupId);
}