package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.FlowerGroupDTO;
import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.dto.FlowerSummaryDTO;
import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.model.FlowerImage;
import com.example.leminhflowerBE.repository.FlowerGroupRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlowerGroupService {

    private final FlowerGroupRepository repo;

    public FlowerGroupService(FlowerGroupRepository repo) {
        this.repo = repo;
    }

    // ✅ Random danh sách hoa thuộc nhiều nhóm khác nhau (trả về FlowerSummaryDTO)
    public List<FlowerSummaryDTO> getRandomFlowers(int itemCount) {
        List<FlowerGroup> groups = repo.findAll();

        if (groups.isEmpty()) {
            throw new RuntimeException("❌ Không có nhóm hoa nào trong hệ thống");
        }

        List<FlowerSummaryDTO> result = new ArrayList<>();
        Random random = new Random();

        // 🔀 Trộn danh sách nhóm để random
        Collections.shuffle(groups);

        for (FlowerGroup group : groups) {
            if (result.size() >= itemCount) break;

            List<Flower> flowers = group.getFlowers();
            if (flowers != null && !flowers.isEmpty()) {
                Flower randomFlower = flowers.get(random.nextInt(flowers.size()));

                // ✅ Convert danh sách ảnh sang DTO
                List<FlowerImageDTO> imageDTOs = (randomFlower.getImages() != null)
                        ? randomFlower.getImages().stream()
                        .map(img -> new FlowerImageDTO(
                                img.getImageId(),
                                img.getImageUrl(),
                                img.getImageType() != null ? img.getImageType().name() : null,
                                randomFlower.getFlowerId()
                        ))
                        .collect(Collectors.toList())
                        : List.of();

                result.add(new FlowerSummaryDTO(
                        randomFlower.getName(),
                        randomFlower.getPrice() != null ? randomFlower.getPrice() : 0.0,
                        group.getGroupName(), // ✅ thêm tên nhóm tại đây
                        imageDTOs
                ));
            }
        }

        // 🔁 Nếu chưa đủ itemCount thì random thêm từ toàn bộ hoa
        if (result.size() < itemCount) {
            List<Flower> allFlowers = groups.stream()
                    .flatMap(g -> g.getFlowers().stream())
                    .collect(Collectors.toList());
            Collections.shuffle(allFlowers);

            for (Flower f : allFlowers) {
                if (result.size() >= itemCount) break;

                boolean exists = result.stream()
                        .anyMatch(dto -> dto.getName().equals(f.getName()));

                if (!exists) {
                    List<FlowerImageDTO> imageDTOs = (f.getImages() != null)
                            ? f.getImages().stream()
                            .map(img -> new FlowerImageDTO(
                                    img.getImageId(),
                                    img.getImageUrl(),
                                    img.getImageType() != null ? img.getImageType().name() : null,
                                    f.getFlowerId()
                            ))
                            .collect(Collectors.toList())
                            : List.of();

                    // ✅ tìm groupName của flower (nếu có)
                    String groupName = groups.stream()
                            .filter(g -> g.getFlowers().contains(f))
                            .map(FlowerGroup::getGroupName)
                            .findFirst()
                            .orElse("Unknown");

                    result.add(new FlowerSummaryDTO(
                            f.getName(),
                            f.getPrice() != null ? f.getPrice() : 0.0,
                            groupName,
                            imageDTOs
                    ));
                }
            }
        }

        return result;
    }



    // ✅ Lấy toàn bộ danh sách nhóm + danh sách hoa trong nhóm
    public List<FlowerGroupDTO> getAll() {
        return Optional.of(repo.findAll())
                .orElse(List.of())
                .stream()
                .map(FlowerGroupDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // ✅ Lấy nhóm theo ID + tổng số hoa trong nhóm
    public Map<String, Object> getGroupWithCount(Long id) {
        FlowerGroup group = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Group not found with id: " + id));

        long count;
        try {
            count = repo.countByGroup_GroupId(id);
        } catch (Exception e) {
            count = 0;
        }

        return Map.of(
                "group", FlowerGroupDTO.fromEntity(group),
                "flowerCount", count
        );
    }

    // ✅ Lấy nhóm theo ID
    public FlowerGroupDTO getById(Long id) {
        return repo.findById(id)
                .map(FlowerGroupDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("❌ Group not found with id: " + id));
    }

    // ✅ Tạo mới nhóm
    public FlowerGroupDTO create(FlowerGroup group) {
        if (group == null) throw new RuntimeException("❌ Group data cannot be null");
        FlowerGroup saved = repo.save(group);
        return FlowerGroupDTO.fromEntity(saved);
    }

    // ✅ Cập nhật nhóm (nếu giá trị mới null thì giữ giá trị cũ)
    public FlowerGroupDTO update(Long id, FlowerGroup group) {
        FlowerGroup existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Group not found with id: " + id));

        if (group.getGroupName() != null && !group.getGroupName().isBlank()) {
            existing.setGroupName(group.getGroupName());
        }
        if (group.getDescription() != null && !group.getDescription().isBlank()) {
            existing.setDescription(group.getDescription());
        }

        FlowerGroup updated = repo.save(existing);
        return FlowerGroupDTO.fromEntity(updated);
    }

    // ✅ Xoá nhóm
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("❌ Cannot delete — group not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
