package com.example.leminhflowerBE.repository;


import com.example.leminhflowerBE.model.OtherServiceImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OtherServiceImageRepository extends JpaRepository<OtherServiceImage, Long> {
    List<OtherServiceImage> findByServiceId(Long serviceId);
    void deleteByServiceId(Long serviceId);
}
