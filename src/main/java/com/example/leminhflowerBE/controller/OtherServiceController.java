package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.model.OtherService;
import com.example.leminhflowerBE.response.ApiResponse;
import com.example.leminhflowerBE.service.OtherServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/other-services")
public class OtherServiceController {

    private final OtherServiceService service;

    public OtherServiceController(OtherServiceService service) {
        this.service = service;
    }

    // 🟢 Lấy tất cả
    @GetMapping
    public ResponseEntity<ApiResponse<List<OtherService>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách thành công", service.getAll()));
    }

    // 🔍 Tìm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OtherService>> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(s -> ResponseEntity.ok(new ApiResponse<>(true, "Tìm thấy dịch vụ", s)))
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "Không tìm thấy dịch vụ", null)));
    }

    // 🔍 Tìm theo type
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<OtherService>>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy theo type", service.getByType(type)));
    }

    // 🔍 Tìm theo title gần đúng
    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<OtherService>>> searchByTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Kết quả tìm kiếm theo tiêu đề", service.searchByTitle(keyword)));
    }

    // 🔍 Tìm theo mô tả gần đúng
    @GetMapping("/search/description")
    public ResponseEntity<ApiResponse<List<OtherService>>> searchByDescription(@RequestParam String keyword) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Kết quả tìm kiếm theo mô tả", service.searchByDescription(keyword)));
    }

    // 🟢 Thêm 1
    @PostMapping
    public ResponseEntity<ApiResponse<OtherService>> create(@RequestBody OtherService newService) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Thêm dịch vụ thành công", service.create(newService)));
    }

    // 🟢 Thêm nhiều
    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<OtherService>>> createBatch(@RequestBody List<OtherService> services) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Thêm nhiều dịch vụ thành công", service.createBatch(services)));
    }

    // 🟠 Cập nhật 1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OtherService>> update(@PathVariable Long id, @RequestBody OtherService updated) {
        OtherService result = service.update(id, updated);
        if (result == null)
            return ResponseEntity.ok(new ApiResponse<>(false, "Không tìm thấy dịch vụ để cập nhật", null));
        return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật thành công", result));
    }

    // 🟠 Cập nhật nhiều
    @PutMapping("/batch")
    public ResponseEntity<ApiResponse<List<OtherService>>> updateBatch(@RequestBody List<OtherService> services) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật nhiều dịch vụ thành công", service.updateBatch(services)));
    }

    // 🔴 Xóa 1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (!deleted)
            return ResponseEntity.ok(new ApiResponse<>(false, "Không tìm thấy dịch vụ để xóa", null));
        return ResponseEntity.ok(new ApiResponse<>(true, "Xóa thành công", null));
    }

    // 🔴 Xóa nhiều
    @DeleteMapping("/batch")
    public ResponseEntity<ApiResponse<Void>> deleteBatch(@RequestBody List<Long> ids) {
        service.deleteBatch(ids);
        return ResponseEntity.ok(new ApiResponse<>(true, "Đã xóa nhiều dịch vụ", null));
    }

    // 🔴 Xóa tất cả
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok(new ApiResponse<>(true, "Đã xóa toàn bộ dịch vụ", null));
    }
}
