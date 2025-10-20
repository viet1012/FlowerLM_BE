package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.mapper.FlowerImageMapper;
import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.model.FlowerImage;
import com.example.leminhflowerBE.repository.FlowerImageRepository;
import com.example.leminhflowerBE.repository.FlowerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowerImageService {

    private final FlowerImageRepository repo;
    private final FlowerRepository flowerRepo;

    public FlowerImageService(FlowerImageRepository repo, FlowerRepository flowerRepo) {
        this.repo = repo;
        this.flowerRepo = flowerRepo;
    }

    public List<FlowerImageDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(FlowerImageMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FlowerImageDTO getById(Long id) {
        FlowerImage image = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
        return FlowerImageMapper.toDTO(image);
    }

    public FlowerImageDTO create(FlowerImageDTO dto) {
        Flower flower = flowerRepo.findById(dto.getFlowerId())
                .orElseThrow(() -> new RuntimeException("Flower not found with id: " + dto.getFlowerId()));

        FlowerImage image = new FlowerImage();
        image.setFlower(flower);
        image.setImageUrl(dto.getImageUrl());
        image.setImageType(FlowerImage.ImageType.valueOf(dto.getImageType()));

        FlowerImage saved = repo.save(image);
        return FlowerImageMapper.toDTO(saved);
    }

    public FlowerImageDTO update(Long id, FlowerImageDTO dto) {
        FlowerImage image = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));

        image.setImageUrl(dto.getImageUrl());
        image.setImageType(FlowerImage.ImageType.valueOf(dto.getImageType()));

        FlowerImage updated = repo.save(image);
        return FlowerImageMapper.toDTO(updated);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
