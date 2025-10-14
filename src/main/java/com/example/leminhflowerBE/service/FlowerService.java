package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.FlowerDTO;
import com.example.leminhflowerBE.mapper.FlowerMapper;
import com.example.leminhflowerBE.model.Flower;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.model.FlowerImage;
import com.example.leminhflowerBE.repository.FlowerGroupRepository;
import com.example.leminhflowerBE.repository.FlowerRepository;
import com.example.leminhflowerBE.request.FlowerRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowerService {

    private final FlowerRepository repo;

    public FlowerService(FlowerRepository repo) {
        this.repo = repo;
    }

    @Autowired
    private FlowerGroupRepository flowerGroupRepository;

    public List<FlowerDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<Flower> getByGroupId(Long groupId) {
        return repo.findByGroup_GroupId(groupId);
    }

    public Flower getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flower not found with id: " + id));
    }

    public Flower create(FlowerRequest request) {
        FlowerGroup group = flowerGroupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhóm hoa với id = " + request.getGroupId()));

        Flower flower = new Flower();
        flower.setGroup(group);
        flower.setName(request.getName());
        flower.setLifespan(request.getLifespan());
        flower.setOrigin(request.getOrigin());
        flower.setDescription(request.getDescription());
        flower.setFeature(request.getFeature());
        flower.setMeaning(request.getMeaning());

        if (request.getImages() != null) {
            flower.setImages(request.getImages().stream().map(imgReq -> {
                FlowerImage img = new FlowerImage();
                img.setFlower(flower);
                img.setImageUrl(imgReq.getImageUrl());
                img.setImageType(FlowerImage.ImageType.valueOf(imgReq.getImageType()));
                return img;
            }).collect(Collectors.toList()));
        }

        return repo.save(flower);
    }



    public Flower update(Long id, Flower flower) {
        Flower existing = getById(id);
        existing.setName(flower.getName());
        existing.setLifespan(flower.getLifespan());
        existing.setOrigin(flower.getOrigin());
        existing.setDescription(flower.getDescription());
        existing.setFeature(flower.getFeature());
        existing.setMeaning(flower.getMeaning());
        existing.setGroup(flower.getGroup()); // cập nhật group nếu có thay đổi
        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Flower flower = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hoa với id = " + id));

        repo.delete(flower); // Hibernate sẽ tự xóa cả FlowerImage nếu có cascade
    }

}
