package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.FlowerGroupDTO;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.repository.FlowerGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlowerGroupService {

    private final FlowerGroupRepository repo;

    public FlowerGroupService(FlowerGroupRepository repo) {
        this.repo = repo;
    }

    // ✅ Lấy toàn bộ danh sách nhóm + danh sách hoa trong nhóm
    public List<FlowerGroupDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(FlowerGroupDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // ✅ Lấy nhóm theo ID + tổng số hoa trong nhóm
    public Map<String, Object> getGroupWithCount(Long id) {
        FlowerGroup group = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        long count = repo.countByGroup_GroupId(id);

        return Map.of(
                "group", FlowerGroupDTO.fromEntity(group),
                "flowerCount", count
        );
    }

    // ✅ Lấy nhóm theo ID
    public FlowerGroupDTO getById(Long id) {
        FlowerGroup group = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
        return FlowerGroupDTO.fromEntity(group);
    }

    // ✅ Tạo mới nhóm
    public FlowerGroupDTO create(FlowerGroup group) {
        FlowerGroup saved = repo.save(group);
        return FlowerGroupDTO.fromEntity(saved);
    }

    // ✅ Cập nhật nhóm
    public FlowerGroupDTO update(Long id, FlowerGroup group) {
        FlowerGroup existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));

        existing.setGroupName(group.getGroupName());
        existing.setDescription(group.getDescription());

        FlowerGroup updated = repo.save(existing);
        return FlowerGroupDTO.fromEntity(updated);
    }

    // ✅ Xoá nhóm
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
