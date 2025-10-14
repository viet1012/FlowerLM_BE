package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.repository.FlowerGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FlowerGroupService {

    private final FlowerGroupRepository repo;

    public FlowerGroupService(FlowerGroupRepository repo) {
        this.repo = repo;
    }

    public List<FlowerGroup> getAll() {
        return repo.findAll();
    }

    public Map<String, Object> getGroupWithCount(Long id) {
        FlowerGroup group = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        long count = repo.countByGroup_GroupId(id);
        return Map.of("group", group, "flowerCount", count);
    }

    public FlowerGroup getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + id));
    }

    public FlowerGroup create(FlowerGroup group) {
        return repo.save(group);
    }

    public FlowerGroup update(Long id, FlowerGroup group) {
        FlowerGroup existing = getById(id);
        existing.setGroupName(group.getGroupName());
        existing.setDescription(group.getDescription());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
