package com.example.leminhflowerBE.repository;

import com.example.leminhflowerBE.model.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerRepository extends JpaRepository<Flower, Long> {

    // ✅ Lấy hoa theo nhiều groupId
    List<Flower> findByGroup_GroupIdIn(List<Long> groupIds);
}