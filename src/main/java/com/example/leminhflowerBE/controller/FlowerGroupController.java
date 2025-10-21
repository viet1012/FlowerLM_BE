package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.FlowerGroupDTO;
import com.example.leminhflowerBE.dto.FlowerSummaryDTO;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.service.FlowerGroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class FlowerGroupController {

    private final FlowerGroupService service;

    public FlowerGroupController(FlowerGroupService service) {
        this.service = service;
    }

    // ✅ 1️⃣ API random danh sách hoa
    @GetMapping("/random")
    public ResponseEntity<List<FlowerSummaryDTO>> getRandomFlowers(
            @RequestParam(defaultValue = "10") int count
    ) {
        return ResponseEntity.ok(service.getRandomFlowers(count));
    }

    // 🟢 Lấy tất cả nhóm hoa (trả về danh sách DTO)
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<FlowerGroupDTO> groups = service.getAll();
        if (groups.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Không có nhóm hoa nào trong hệ thống.");
        }
        return ResponseEntity.ok(groups);
    }

    // 🟡 Lấy nhóm + tổng số hoa trong nhóm
    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        try {
            Map<String, Object> groupData = service.getGroupWithCount(id);
            return ResponseEntity.ok(groupData);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Không tìm thấy nhóm hoa với ID = " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("⚠️ Lỗi khi truy vấn nhóm hoa: " + e.getMessage());
        }
    }

    // 🔵 Lấy thông tin chi tiết nhóm (trả về DTO)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            FlowerGroupDTO dto = service.getById(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Không tìm thấy nhóm hoa với ID = " + id);
        }
    }

    // 🟢 Tạo nhóm hoa mới
    @PostMapping
    public ResponseEntity<?> create(@RequestBody FlowerGroup group) {
        try {
            FlowerGroupDTO created = service.create(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("⚠️ Không thể tạo nhóm hoa: " + e.getMessage());
        }
    }

    // 🟠 Cập nhật nhóm hoa
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody FlowerGroup group) {
        try {
            FlowerGroupDTO updated = service.update(id, group);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Không tìm thấy nhóm hoa để cập nhật (ID = " + id + ")");
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
