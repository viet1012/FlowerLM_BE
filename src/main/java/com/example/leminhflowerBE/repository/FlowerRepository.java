package com.example.leminhflowerBE.repository;

import com.example.leminhflowerBE.model.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlowerRepository extends JpaRepository<Flower, Long> {

    // ✅ Lấy hoa theo nhiều groupId
    List<Flower> findByGroup_GroupIdIn(List<Long> groupIds);

    @Query("""
        SELECT f FROM Flower f
        WHERE (:name IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:groupId IS NULL OR f.group.groupId = :groupId)
        AND (:origin IS NULL OR LOWER(f.origin) LIKE LOWER(CONCAT('%', :origin, '%')))
        AND (:lifespan IS NULL OR LOWER(f.lifespan) LIKE LOWER(CONCAT('%', :lifespan, '%')))
        AND (:priceMin IS NULL OR f.price >= :priceMin)
        AND (:priceMax IS NULL OR f.price <= :priceMax)
    """)
    List<Flower> filterFlowers(
            @Param("name") String name,
            @Param("groupId") Long groupId,
            @Param("origin") String origin,
            @Param("lifespan") String lifespan,
            @Param("priceMin") Double priceMin,
            @Param("priceMax") Double priceMax
    );
}