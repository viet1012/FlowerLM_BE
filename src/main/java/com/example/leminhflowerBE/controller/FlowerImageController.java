package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.FlowerImageDTO;
import com.example.leminhflowerBE.response.ApiResponse;
import com.example.leminhflowerBE.service.FlowerImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class FlowerImageController {

    private final FlowerImageService service;

    public FlowerImageController(FlowerImageService service) {
        this.service = service;
    }

    // 🟢 Lấy tất cả ảnh hoa
    @GetMapping
    public ResponseEntity<ApiResponse<List<FlowerImageDTO>>> getAll() {
        try {
            List<FlowerImageDTO> images = service.getAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách ảnh thành công", images));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi khi lấy danh sách ảnh: " + e.getMessage(), null));
        }
    }
    // 🟡 Lấy ảnh theo ID
    @GetMapping("/byFlower/{id}")
    public ResponseEntity<ApiResponse<List<FlowerImageDTO>>> getByFlowerId(@PathVariable Long id) {
        try {
            List<FlowerImageDTO> dto = service.getByFlowerId(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy ảnh thành công", dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy ảnh với FlowerID = " + id, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi lấy ảnh: " + e.getMessage(), null));
        }
    }
    // 🟡 Lấy ảnh theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerImageDTO>> getById(@PathVariable Long id) {
        try {
            FlowerImageDTO dto = service.getById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy ảnh thành công", dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy ảnh với ID = " + id, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi lấy ảnh: " + e.getMessage(), null));
        }
    }

    // 🔵 Tạo ảnh mới
    @PostMapping
    public ResponseEntity<ApiResponse<FlowerImageDTO>> create(@RequestBody FlowerImageDTO dto) {
        try {
            FlowerImageDTO created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Tạo ảnh hoa thành công", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không thể tạo ảnh hoa: " + e.getMessage(), null));
        }
    }

    // 🟠 Cập nhật ảnh
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerImageDTO>> update(@PathVariable Long id, @RequestBody FlowerImageDTO dto) {
        try {
            FlowerImageDTO updated = service.update(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật ảnh hoa thành công", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy ảnh hoa để cập nhật (ID = " + id + ")", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi cập nhật ảnh hoa: " + e.getMessage(), null));
        }
    }

    // 🔴 Xoá ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã xoá ảnh hoa có ID = " + id, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không thể xoá ảnh hoa: " + e.getMessage(), null));
        }
    }
}
