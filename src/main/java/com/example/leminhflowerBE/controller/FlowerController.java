package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.FlowerDTO;
import com.example.leminhflowerBE.request.FlowerRequest;
import com.example.leminhflowerBE.response.ApiResponse;
import com.example.leminhflowerBE.service.FlowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flowers")
public class FlowerController {

    private final FlowerService service;

    public FlowerController(FlowerService service) {
        this.service = service;
    }

    // ✅ Lấy toàn bộ danh sách hoa
    @GetMapping
    public ResponseEntity<ApiResponse<List<FlowerDTO>>> getAll() {
        try {
            List<FlowerDTO> flowers = service.getAll();
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách hoa thành công", flowers));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }


    // ✅ Lọc hoa theo nhiều groupId
    @GetMapping("/groups")
    public ResponseEntity<ApiResponse<List<FlowerDTO>>> getByGroupIds(
            @RequestParam List<Long> groupIds
    ) {
        try {
            List<FlowerDTO> flowers = service.getByGroupIds(groupIds);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Lấy hoa theo nhiều nhóm thành công.", flowers)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }


    // ✅ Lấy nhiều hoa theo danh sách ID
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<FlowerDTO>>> getByIds(
            @RequestParam List<Long> ids
    ) {
        try {
            List<FlowerDTO> flowers = service.getByIds(ids);
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Lấy danh sách hoa thành công.", flowers)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }


    // ✅ Tạo mới nhiều hoa cùng lúc
    @PostMapping("/batch")
    public ResponseEntity<ApiResponse<List<FlowerDTO>>> createMultiple(@RequestBody List<FlowerRequest> requests) {
        try {
            List<FlowerDTO> result = service.createMultiple(requests);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tạo mới " + result.size() + " hoa thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // ✅ Tạo mới 1 hoa
    @PostMapping
    public ResponseEntity<ApiResponse<FlowerDTO>> create(@RequestBody FlowerRequest flower) {
        try {
            FlowerDTO result = service.create(flower);
            return ResponseEntity.ok(new ApiResponse<>(true, "Thêm hoa thành công", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // ✅ Cập nhật hoa
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlowerDTO>> update(@PathVariable Long id, @RequestBody FlowerRequest flower) {
        try {
            FlowerDTO updated = service.update(id, flower);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật hoa thành công", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // ✅ Lấy ngẫu nhiên một số hoa
    @GetMapping("/random")
    public ResponseEntity<ApiResponse<List<FlowerDTO>>> getRandomFlowers(@RequestParam(defaultValue = "3") int count) {
        try {
            List<FlowerDTO> randomList = service.getRandomFlowers(count);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy " + count + " hoa ngẫu nhiên thành công", randomList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // ✅ Xoá nhiều hoa cùng lúc
    @DeleteMapping("/many")
    public ResponseEntity<ApiResponse<String>> deleteMany(@RequestBody List<Long> ids) {
        try {
            service.deleteMany(ids);
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã xoá " + ids.size() + " hoa.", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    // ✅ Xoá 1 hoa
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Xoá hoa thành công", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
