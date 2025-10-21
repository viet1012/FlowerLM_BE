package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.model.FlowerFeature;
import com.example.leminhflowerBE.response.ApiResponse;
import com.example.leminhflowerBE.service.FlowerFeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flower-features")
@CrossOrigin(origins = "*")
public class FlowerFeatureController {

    private final FlowerFeatureService service;

    public FlowerFeatureController(FlowerFeatureService service) {
        this.service = service;
    }

    // 🟢 Lấy tất cả đặc điểm hoa
    @GetMapping
    public ResponseEntity<ApiResponse<List<FlowerFeature>>> getAll() {
        List<FlowerFeature> features = service.getAllFeatures();
        if (features.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(true, "Không có đặc điểm hoa nào.", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách đặc điểm hoa thành công.", features));
    }

    // 🟡 Lấy đặc điểm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerFeature>> getById(@PathVariable Long id) {
        return service.getFeatureById(id)
                .map(feature -> ResponseEntity.ok(
                        new ApiResponse<>(true, "Lấy thông tin đặc điểm hoa thành công.", feature)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Không tìm thấy đặc điểm hoa với ID = " + id, null)));
    }

    // 🟠 Tạo mới danh sách đặc điểm hoa
    @PostMapping
    public ResponseEntity<ApiResponse<List<FlowerFeature>>> createFeatures(@RequestBody List<FlowerFeature> features) {
        try {
            if (features.size() > 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(false, "Chỉ được phép tạo tối đa 6 đặc điểm hoa cùng lúc.", null));
            }
            List<FlowerFeature> created = service.createFeatures(features);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Tạo đặc điểm hoa thành công.", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi tạo đặc điểm hoa: " + e.getMessage(), null));
        }
    }

    // 🔵 Cập nhật đặc điểm hoa
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerFeature>> update(@PathVariable Long id, @RequestBody FlowerFeature feature) {
        try {
            FlowerFeature updated = service.updateFeature(id, feature);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật đặc điểm hoa thành công.", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy đặc điểm hoa để cập nhật (ID = " + id + ")", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi cập nhật đặc điểm hoa: " + e.getMessage(), null));
        }
    }

    // 🔴 Xóa toàn bộ đặc điểm
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAll() {
        try {
            service.deleteAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã xóa toàn bộ đặc điểm hoa.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi khi xóa tất cả đặc điểm hoa: " + e.getMessage(), null));
        }
    }

    // 🔴 Xóa đặc điểm theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            service.deleteFeature(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã xóa đặc điểm hoa có ID = " + id, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không thể xóa đặc điểm hoa: " + e.getMessage(), null));
        }
    }
}
