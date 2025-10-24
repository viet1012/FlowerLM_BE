package com.example.leminhflowerBE.repository;


import com.example.leminhflowerBE.model.FlowerImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerImageRepository extends JpaRepository<FlowerImage, Long> {
    List<FlowerImage> findByFlower_FlowerId(Long flowerId); // ✅ thêm hàm này
}
