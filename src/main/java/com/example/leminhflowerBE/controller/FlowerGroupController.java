package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.*;
import com.example.leminhflowerBE.model.FlowerGroup;
import com.example.leminhflowerBE.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<FlowerSummaryDTO>>> getRandomFlowers(
            @RequestParam(defaultValue = "10") int count
    ) {
        try {
            List<FlowerSummaryDTO> result = service.getRandomFlowers(count);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách hoa ngẫu nhiên thành công", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi lấy hoa ngẫu nhiên: " + e.getMessage(), null));
        }
    }
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<List<FlowerGroupCountDTO>>> getAllGroupsWithCount() {
        try {
            List<FlowerGroupCountDTO> groups = service.getAllWithCount();
            if (groups.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Không có nhóm hoa nào trong hệ thống", groups));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách nhóm hoa thành công", groups));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi khi lấy danh sách nhóm hoa: " + e.getMessage(), null));
        }
    }

    // 🟢 Lấy tất cả nhóm hoa
    @GetMapping
    public ResponseEntity<ApiResponse<List<FlowerGroupDTO>>> getAll() {
        try {
            List<FlowerGroupDTO> groups = service.getAll();
            if (groups.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Không có nhóm hoa nào trong hệ thống", groups));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách nhóm hoa thành công", groups));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi khi lấy danh sách nhóm hoa: " + e.getMessage(), null));
        }
    }

    // 🟡 Lấy nhóm + tổng số hoa trong nhóm
    @GetMapping("/group/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getGroupById(@PathVariable Long id) {
        try {
            Map<String, Object> groupData = service.getGroupWithCount(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy nhóm hoa thành công", groupData));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy nhóm hoa với ID = " + id, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi khi truy vấn nhóm hoa: " + e.getMessage(), null));
        }
    }

    // 🔵 Lấy thông tin chi tiết nhóm
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerGroupDTO>> getById(@PathVariable Long id) {
        try {
            FlowerGroupDTO dto = service.getById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy thông tin nhóm hoa thành công", dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy nhóm hoa với ID = " + id, null));
        }
    }

    // 🟢 Tạo nhóm hoa mới
    @PostMapping
    public ResponseEntity<ApiResponse<FlowerGroupDTO>> create(@RequestBody CreateFlowerGroupRequest group) {
        try {
            FlowerGroupDTO created = service.create(group);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Tạo nhóm hoa mới thành công", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không thể tạo nhóm hoa: " + e.getMessage(), null));
        }
    }

    // 🟠 Cập nhật nhóm hoa
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerGroupDTO>> update(@PathVariable Long id, @RequestBody UpdateFlowerGroupRequest group) {
        try {
            FlowerGroupDTO updated = service.update(id, group);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật nhóm hoa thành công", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy nhóm hoa để cập nhật (ID = " + id + ")", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi cập nhật nhóm hoa: " + e.getMessage(), null));
        }
    }
    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> deleteAll() {
        try {
            service.deleteAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã xóa nhóm hoa có ID = ", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không thể xóa nhóm hoa: " + e.getMessage(), null));
        }
    }
    // 🔴 Xóa nhóm hoa
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã xóa nhóm hoa có ID = " + id, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không thể xóa nhóm hoa: " + e.getMessage(), null));
        }
    }
}
