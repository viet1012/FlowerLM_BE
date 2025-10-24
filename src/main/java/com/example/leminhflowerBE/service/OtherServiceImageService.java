package com.example.leminhflowerBE.service;


import com.example.leminhflowerBE.model.OtherService;
import com.example.leminhflowerBE.model.OtherServiceImage;
import com.example.leminhflowerBE.repository.OtherServiceImageRepository;
import com.example.leminhflowerBE.repository.OtherServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherServiceImageService {

    private final OtherServiceImageRepository imageRepo;
    private final OtherServiceRepository serviceRepo;

    public OtherServiceImageService(OtherServiceImageRepository imageRepo, OtherServiceRepository serviceRepo) {
        this.imageRepo = imageRepo;
        this.serviceRepo = serviceRepo;
    }

    // 🟢 Lấy toàn bộ ảnh theo serviceId
    public List<OtherServiceImage> getImagesByService(Long serviceId) {
        return imageRepo.findByServiceId(serviceId);
    }

    // 🔵 Thêm 1 ảnh
    public OtherServiceImage addImage(Long serviceId, String imageUrl) {
        OtherService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ id=" + serviceId));
        OtherServiceImage img = new OtherServiceImage();
        img.setService(service);
        img.setImageUrl(imageUrl);
        return imageRepo.save(img);
    }

    // 🔵 Thêm nhiều ảnh
    public List<OtherServiceImage> addImages(Long serviceId, List<String> urls) {
        OtherService service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ id=" + serviceId));

        List<OtherServiceImage> list = urls.stream().map(url -> {
            OtherServiceImage img = new OtherServiceImage();
            img.setService(service);
            img.setImageUrl(url);
            return img;
        }).toList();

        return imageRepo.saveAll(list);
    }

    // 🟠 Sửa nhiều ảnh
    public List<OtherServiceImage> updateImages(List<OtherServiceImage> images) {
        return imageRepo.saveAll(images);
    }

    // 🔴 Xóa 1 ảnh
    public void deleteImage(Long imageId) {
        imageRepo.deleteById(imageId);
    }

    // 🔴 Xóa tất cả ảnh của 1 service
    public void deleteAllByService(Long serviceId) {
        imageRepo.deleteByServiceId(serviceId);
    }
}
