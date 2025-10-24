package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.model.OtherServiceImage;
import com.example.leminhflowerBE.response.ApiResponse;
import com.example.leminhflowerBE.service.OtherServiceImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/other-service/images")
public class OtherServiceImageController {

    private final OtherServiceImageService service;

    public OtherServiceImageController(OtherServiceImageService service) {
        this.service = service;
    }

    // 🟢 Lấy danh sách ảnh theo Service ID
    @GetMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<List<OtherServiceImage>>> getImages(@PathVariable Long serviceId) {
        List<OtherServiceImage> images = service.getImagesByService(serviceId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách ảnh thành công", images));
    }

    // 🔵 Thêm 1 ảnh
    @PostMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<OtherServiceImage>> addImage(
            @PathVariable Long serviceId,
            @RequestBody String imageUrl) {
        OtherServiceImage image = service.addImage(serviceId, imageUrl);
        return ResponseEntity.ok(new ApiResponse<>(true, "Thêm ảnh thành công", image));
    }

    // 🔵 Thêm nhiều ảnh
    @PostMapping("/{serviceId}/batch")
    public ResponseEntity<ApiResponse<List<OtherServiceImage>>> addImages(
            @PathVariable Long serviceId,
            @RequestBody List<String> imageUrls) {
        List<OtherServiceImage> images = service.addImages(serviceId, imageUrls);
        return ResponseEntity.ok(new ApiResponse<>(true, "Thêm nhiều ảnh thành công", images));
    }

    // 🟠 Cập nhật nhiều ảnh
    @PutMapping("/batch")
    public ResponseEntity<ApiResponse<List<OtherServiceImage>>> updateImages(
            @RequestBody List<OtherServiceImage> images) {
        List<OtherServiceImage> updated = service.updateImages(images);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật ảnh thành công", updated));
    }

    // 🔴 Xóa 1 ảnh
    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse<Void>> deleteImage(@PathVariable Long imageId) {
        service.deleteImage(imageId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Xóa ảnh thành công", null));
    }

    // 🔴 Xóa tất cả ảnh theo service
    @DeleteMapping("/byService/{serviceId}")
    public ResponseEntity<ApiResponse<Void>> deleteAll(@PathVariable Long serviceId) {
        service.deleteAllByService(serviceId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Xóa tất cả ảnh theo dịch vụ thành công", null));
    }
}

