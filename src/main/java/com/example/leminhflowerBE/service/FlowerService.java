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

    @Autowired
    private FlowerGroupRepository flowerGroupRepository;

    public FlowerService(FlowerRepository repo) {
        this.repo = repo;
    }

    // ✅ Lấy tất cả hoa
    public List<FlowerDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy danh sách hoa theo groupId, trả về dạng DTO
    public List<FlowerDTO> getByGroupId(Long groupId) {
        return repo.findByGroup_GroupId(groupId)
                .stream()
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy 1 hoa theo ID, trả về DTO
    public FlowerDTO getById(Long id) {
        Flower flower = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flower not found with id: " + id));
        return FlowerMapper.toDTO(flower);
    }

    // ✅ Thêm hoa mới
    public FlowerDTO create(FlowerRequest request) {
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
        flower.setPrice(request.getPrice());

        if (request.getImages() != null) {
            flower.setImages(
                    request.getImages().stream().map(imgReq -> {
                        FlowerImage img = new FlowerImage();
                        img.setFlower(flower);
                        img.setImageUrl(imgReq.getImageUrl());
                        img.setImageType(FlowerImage.ImageType.valueOf(imgReq.getImageType()));
                        return img;
                    }).collect(Collectors.toList())
            );

        }

        Flower saved = repo.save(flower);
        return FlowerMapper.toDTO(saved);
    }

    // ✅ Cập nhật hoa
    @Transactional
    public FlowerDTO update(Long id, FlowerRequest request) {
        Flower existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flower not found with id: " + id));

        // ✅ Chỉ update khi có giá trị mới
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getLifespan() != null) existing.setLifespan(request.getLifespan());
        if (request.getOrigin() != null) existing.setOrigin(request.getOrigin());
        if (request.getDescription() != null) existing.setDescription(request.getDescription());
        if (request.getFeature() != null) existing.setFeature(request.getFeature());
        if (request.getMeaning() != null) existing.setMeaning(request.getMeaning());
        if (request.getPrice() != null) existing.setPrice(request.getPrice());

        // ✅ Cập nhật ảnh (nếu có gửi trong request)
        if (request.getImages() != null) {
            existing.getImages().clear();
            existing.getImages().addAll(
                    request.getImages().stream().map(imgReq -> {
                        FlowerImage img = new FlowerImage();
                        img.setFlower(existing);
                        img.setImageUrl(imgReq.getImageUrl());
                        img.setImageType(FlowerImage.ImageType.valueOf(imgReq.getImageType()));
                        return img;
                    }).toList()
            );
        }

        Flower updated = repo.save(existing);
        return FlowerMapper.toDTO(updated);
    }



    // ✅ Xóa hoa
    @Transactional
    public void delete(Long id) {
        Flower flower = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hoa với id = " + id));
        repo.delete(flower);
    }
}
