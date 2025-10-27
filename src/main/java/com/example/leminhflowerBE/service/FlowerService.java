package com.example.leminhflowerBE.service;

import com.example.leminhflowerBE.dto.FlowerDTO;
import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.mapper.FlowerImageMapper;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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

    public FlowerDTO getById(Long id) {
        Flower flower = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Flower not found with id: " + id));
        return FlowerMapper.toDTO(flower);
    }


    // ✅ Lấy danh sách hoa theo groupId, trả về dạng DTO
    public List<FlowerDTO> getByGroupIds(List<Long> groupIds) {
        if (groupIds == null || groupIds.isEmpty()) {
            throw new IllegalArgumentException("Danh sách groupId không được để trống!");
        }

        return repo.findByGroup_GroupIdIn(groupIds)
                .stream()
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
    }


    // ✅ Lấy nhiều hoa theo danh sách ID
    public List<FlowerDTO> getByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Danh sách ID không được để trống!");
        }

        return repo.findAllById(ids)
                .stream()
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<FlowerDTO> filterFlowers(String name, Long groupId, String origin, String lifespan,
                                         Double priceMin, Double priceMax) {
        List<Flower> flowers = repo.filterFlowers(name, groupId, origin, lifespan, priceMin, priceMax);
        return flowers.stream().map(FlowerMapper::toDTO).collect(Collectors.toList());
    }

    // ✅ 4 hoa hôm nay
    public List<FlowerDTO> getTodayFlowers() {
        return getFlowersInRange(0, 4);
    }

    // ✅ 4 hoa bán chạy (bỏ qua 4 đầu)
    public List<FlowerDTO> getTopSellerFlowers() {
        return getFlowersInRange(4, 8);
    }

    // ✅ 8 hoa nổi bật (bỏ qua 8 đầu)
    public List<FlowerDTO> getFeaturedFlowers() {
        return getFlowersInRange(8, 16);
    }

    // 🔹 Hàm xử lý cắt danh sách, nếu thiếu thì quay vòng
    private List<FlowerDTO> getFlowersInRange(int start, int end) {
        List<Flower> all = repo.findAll();

        if (all.isEmpty()) return new ArrayList<>();

        List<Flower> result = new ArrayList<>();
        int total = all.size();

        for (int i = start; i < end; i++) {
            result.add(all.get(i % total)); // dùng % để lặp lại nếu thiếu
        }

        return result.stream()
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
    }


    /**
     * ✅ Thêm nhiều hoa cùng lúc
     */
    @Transactional
    public List<FlowerDTO> createMultiple(List<FlowerRequest> requests) {
        List<Flower> flowersToSave = new ArrayList<>();

        for (FlowerRequest request : requests) {
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

            flowersToSave.add(flower);
        }

        List<Flower> savedList = repo.saveAll(flowersToSave);
        return savedList.stream()
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
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

    /**
     * ✅ Lấy ngẫu nhiên N bông hoa (random list)
     */
    public List<FlowerDTO> getRandomFlowers(int count) {
        List<Flower> allFlowers = repo.findAll();

        if (allFlowers.isEmpty()) {
            throw new RuntimeException("Không có hoa nào trong cơ sở dữ liệu!");
        }

        // Xáo trộn danh sách
        Collections.shuffle(allFlowers, new Random());

        // Giới hạn số lượng trả về
        int limit = Math.min(count, allFlowers.size());

        return allFlowers.stream()
                .limit(limit)
                .map(FlowerMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public void deleteAll() {
        repo.deleteAll();
    }

    @Transactional
    public void deleteMany(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Danh sách ID không được trống!");
        }
        repo.deleteAllById(ids);
    }

    // ✅ Xóa hoa
    @Transactional
    public void delete(Long id) {
        Flower flower = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hoa với id = " + id));
        repo.delete(flower);
    }
}
