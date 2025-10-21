package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.BlogPostDTO;
import com.example.leminhflowerBE.response.ApiResponse;
import com.example.leminhflowerBE.service.BlogPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin(origins = "*")
public class BlogPostController {

    private final BlogPostService service;

    public BlogPostController(BlogPostService service) {
        this.service = service;
    }

    // 🟢 Lấy tất cả bài viết
    @GetMapping
    public ResponseEntity<ApiResponse<List<BlogPostDTO>>> getAllPosts() {
        List<BlogPostDTO> posts = service.getAllPosts();
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(true, "Không có bài viết nào trong hệ thống.", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Lấy danh sách bài viết thành công.", posts));
    }

    // 🟡 Lấy bài viết theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BlogPostDTO>> getPostById(@PathVariable Integer id) {
        try {
            BlogPostDTO post = service.getPostById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy bài viết thành công.", post));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy bài viết có ID = " + id, null));
        }
    }

    // 🟣 Lấy bài viết theo slug
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ApiResponse<BlogPostDTO>> getPostBySlug(@PathVariable String slug) {
        try {
            BlogPostDTO post = service.getPostBySlug(slug);
            return ResponseEntity.ok(new ApiResponse<>(true, "Lấy bài viết theo slug thành công.", post));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy bài viết với slug = " + slug, null));
        }
    }

    // 🟠 Tạo bài viết mới
    @PostMapping
    public ResponseEntity<ApiResponse<BlogPostDTO>> createPost(@RequestBody BlogPostDTO dto) {
        try {
            BlogPostDTO created = service.createPost(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Tạo bài viết mới thành công.", created));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Không thể tạo bài viết: " + e.getMessage(), null));
        }
    }

    // 🔵 Cập nhật bài viết
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BlogPostDTO>> updatePost(@PathVariable Integer id, @RequestBody BlogPostDTO dto) {
        try {
            BlogPostDTO updated = service.updatePost(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhật bài viết thành công.", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Không tìm thấy bài viết để cập nhật (ID = " + id + ")", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Lỗi khi cập nhật bài viết: " + e.getMessage(), null));
        }
    }

    // 🔴 Xóa bài viết
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Integer id) {
        try {
            boolean deleted = service.deletePost(id);
            if (deleted) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Đã xóa bài viết có ID = " + id, null));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Không tìm thấy bài viết để xóa (ID = " + id + ")", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Lỗi khi xóa bài viết: " + e.getMessage(), null));
        }
    }
}
