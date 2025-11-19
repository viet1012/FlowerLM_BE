package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.*;
import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.model.FlowerPackage;
import com.example.leminhflowerBE.repository.FlowerPackageRepository;
import com.example.leminhflowerBE.repository.FlowerRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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

        List<FlowerDTO> flowerDTOs =
                flowerPackage.getFlowers() == null ? List.of() :
                        flowerPackage.getFlowers().stream()
                                .map(f -> new FlowerDTO(
                                        f.getFlowerId(),               // flowerId
                                        f.getName(),                   // name
                                        f.getLifespan(),               // lifespan
                                        f.getOrigin(),                 // origin
                                        f.getDescription(),            // description
                                        f.getFeature(),                // feature
                                        f.getMeaning(),                // meaning
                                        f.getGroup() != null ? f.getGroup().getGroupName() : null,
                                        f.getGroup() != null ? f.getGroup().getGroupId() : null,   // groupName
                                        f.getImages().stream()
                                                .map(img -> new FlowerImageDTO(
                                                        img.getImageId(),
                                                        img.getImageUrl(),
                                                        img.getImageType().toString(),   // string type
                                                        img.getFlower().getFlowerId()
                                                ))

                                                .collect(Collectors.toList())                            // List<FlowerImageDTO>
                                ))
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

    public FlowerPackageDTO updateFlowerPackage(Long packageId, FlowerPackageRequestDTO request) {
        FlowerPackage entity = flowerPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("FlowerPackage not found"));

        // ensure list initialized
        if (entity.getFlowers() == null) {
            entity.setFlowers(new ArrayList<>());
        }

        int currentSize = entity.getFlowers().size();
        Integer newCount = request.getCount();

        if (newCount != null && newCount < currentSize) {
            throw new RuntimeException("Không thể giảm count xuống thấp hơn số hoa hiện có (" + currentSize + "). Xóa bớt hoa trước hoặc chọn count lớn hơn.");
        }

        if (request.getType() != null) {
            entity.setType(request.getType());
        }
        if (newCount != null) {
            entity.setCount(newCount);
        }

        FlowerPackage saved = flowerPackageRepository.save(entity);
        return convertToDTO(saved);
    }

    public FlowerPackageDTO updateFlowerInPackage(Long packageId, Long oldFlowerId, Long newFlowerId) {

        FlowerPackage flowerPackage = flowerPackageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("FlowerPackage not found"));

        // Ensure list exists
        if (flowerPackage.getFlowers() == null) {
            throw new RuntimeException("Package chưa có hoa nào để update.");
        }

        Flower oldFlower = flowerRepository.findById(oldFlowerId)
                .orElseThrow(() -> new RuntimeException("Old flower not found"));

        Flower newFlower = flowerRepository.findById(newFlowerId)
                .orElseThrow(() -> new RuntimeException("New flower not found"));

        // Nếu hoa cũ không nằm trong package
        if (!flowerPackage.getFlowers().contains(oldFlower)) {
            throw new RuntimeException("Hoa cần update không tồn tại trong package");
        }

        // Nếu hoa mới đã tồn tại trong package → không cho duplicate
        if (flowerPackage.getFlowers().contains(newFlower)) {
            throw new RuntimeException("Hoa mới đã tồn tại trong package");
        }

        // Thay hoa
        flowerPackage.getFlowers().remove(oldFlower);
        flowerPackage.getFlowers().add(newFlower);

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
