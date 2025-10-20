package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.FlowerGroupDTO;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.repository.FlowerGroupRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlowerGroupService {

    private final FlowerGroupRepository repo;

    public FlowerGroupService(FlowerGroupRepository repo) {
        this.repo = repo;
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

        long count = 0;
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
