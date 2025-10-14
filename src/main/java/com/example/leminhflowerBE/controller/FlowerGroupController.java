package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.service.FlowerGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class FlowerGroupController {

    private final FlowerGroupService service;

    public FlowerGroupController(FlowerGroupService service) {
        this.service = service;
    }

    // 🟢 Lấy tất cả nhóm hoa
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<FlowerGroup> groups = service.getAll();
        if (groups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Không có nhóm hoa nào trong hệ thống.");
        }
        return ResponseEntity.ok(groups);
    }

    // 🟡 Lấy nhóm theo ID + kèm số lượng hoa
    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        try {
            Object group = service.getGroupWithCount(id);
            if (group == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ Không tìm thấy nhóm hoa với ID = " + id);
            }
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("⚠️ Lỗi khi truy vấn nhóm hoa: " + e.getMessage());
        }
    }

    // 🔵 Lấy thông tin chi tiết nhóm
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        FlowerGroup group = service.getById(id);
        if (group == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Không tìm thấy nhóm hoa với ID = " + id);
        }
        return ResponseEntity.ok(group);
    }

    // 🟢 Tạo nhóm hoa mới
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FlowerGroup group) {
        try {
            FlowerGroup created = service.create(group);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("✅ Nhóm hoa '" + created.getGroupName() + "' đã được tạo thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("⚠️ Không thể tạo nhóm hoa: " + e.getMessage());
        }
    }

    // 🟠 Cập nhật nhóm hoa
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FlowerGroup group) {
        try {
            FlowerGroup updated = service.update(id, group);
            if (updated == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ Không tìm thấy nhóm hoa để cập nhật (ID = " + id + ")");
            }
            return ResponseEntity.ok("✅ Cập nhật nhóm hoa thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("⚠️ Lỗi khi cập nhật nhóm hoa: " + e.getMessage());
        }
    }

    // 🔴 Xóa nhóm hoa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("🗑️ Đã xóa nhóm hoa có ID = " + id + " thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("⚠️ Không thể xóa nhóm hoa: " + e.getMessage());
        }
    }
}
