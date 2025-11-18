package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.FlowerDTO;
import com.example.leminhflowerBE.dto.FlowerPackageDTO;
import com.example.leminhflowerBE.dto.FlowerPackageRequestDTO;
import com.example.leminhflowerBE.dto.FlowerResponseDTO;
import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.model.FlowerPackage;
import com.example.leminhflowerBE.repository.FlowerPackageRepository;
import com.example.leminhflowerBE.repository.FlowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlowerPackageService {

    private final FlowerPackageRepository flowerPackageRepository;
    private final FlowerRepository flowerRepository;

    public FlowerPackageService(FlowerPackageRepository flowerPackageRepository, FlowerRepository flowerRepository) {
        this.flowerPackageRepository = flowerPackageRepository;
        this.flowerRepository = flowerRepository;
    }

    // --------------------------
    // Convert entity to DTO
    // --------------------------
    private FlowerPackageDTO convertToDTO(FlowerPackage flowerPackage) {
        List<FlowerResponseDTO> flowerDTOs = flowerPackage.getFlowers() == null ? List.of() :
                flowerPackage.getFlowers().stream()
                        .map(f -> new FlowerResponseDTO(f.getFlowerId(), f.getName()))
                        .collect(Collectors.toList());

        return new FlowerPackageDTO(
                flowerPackage.getId(),
                flowerPackage.getType(),
                flowerPackage.getCount(),
                flowerDTOs
        );
    }

    // Create package
    public FlowerPackageDTO createFlowerPackage(FlowerPackageRequestDTO request) {

        FlowerPackage entity = new FlowerPackage();
        entity.setType(request.getType());
        entity.setCount(request.getCount());
        entity.setFlowers(List.of());  // khởi tạo list rỗng tránh null

        FlowerPackage saved = flowerPackageRepository.save(entity);

        return convertToDTO(saved);
    }


    // Get all – return DTO list
    public List<FlowerPackageDTO> getAllFlowerPackages() {
        return flowerPackageRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get by id
    public FlowerPackageDTO getFlowerPackageById(Long id) {
        FlowerPackage flowerPackage = flowerPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FlowerPackage not found"));
        return convertToDTO(flowerPackage);
    }

    // Add flower to package
    public FlowerPackageDTO addFlowerToPackage(Long packageId, Long flowerId) {
        FlowerPackage flowerPackage = flowerPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("FlowerPackage not found"));

        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new RuntimeException("Flower not found"));

        // Check count limit
        if (flowerPackage.getFlowers() != null &&
                flowerPackage.getFlowers().size() >= flowerPackage.getCount()) {
            throw new RuntimeException("Đã đạt giới hạn số lượng hoa cho type này");
        }

        flowerPackage.getFlowers().add(flower);
        FlowerPackage saved = flowerPackageRepository.save(flowerPackage);

        return convertToDTO(saved);
    }

    // Remove flower
    public FlowerPackageDTO removeFlowerFromPackage(Long packageId, Long flowerId) {
        FlowerPackage flowerPackage = flowerPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("FlowerPackage not found"));

        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new RuntimeException("Flower not found"));

        flowerPackage.getFlowers().remove(flower);
        FlowerPackage saved = flowerPackageRepository.save(flowerPackage);

        return convertToDTO(saved);
    }
}
