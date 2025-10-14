package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.model.FlowerImage;
import com.example.leminhflowerBE.repository.FlowerImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerImageService {

    private final FlowerImageRepository repo;

    public FlowerImageService(FlowerImageRepository repo) {
        this.repo = repo;
    }

    public List<FlowerImage> getAll() {
        return repo.findAll();
    }

    public FlowerImage getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
    }

    public FlowerImage create(FlowerImage image) {
        return repo.save(image);
    }

    public FlowerImage update(Long id, FlowerImage image) {
        FlowerImage existing = getById(id);
        existing.setImageUrl(image.getImageUrl());
        existing.setImageType(image.getImageType());
        existing.setFlower(image.getFlower()); // cập nhật hoa liên kết nếu có thay đổi
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
