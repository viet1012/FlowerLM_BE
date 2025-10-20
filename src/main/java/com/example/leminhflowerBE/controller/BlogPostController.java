package com.example.leminhflowerBE.controller;

import com.example.leminhflowerBE.dto.BlogPostDTO;
import com.example.leminhflowerBE.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin(origins = "*")
public class BlogPostController {

    @Autowired
    private BlogPostService service;

    @GetMapping
    public List<BlogPostDTO> getAllPosts() {
        return service.getAllPosts();
    }

    @GetMapping("/{id}")
    public BlogPostDTO getPostById(@PathVariable Integer id) {
        return service.getPostById(id);
    }

    @GetMapping("/slug/{slug}")
    public BlogPostDTO getPostBySlug(@PathVariable String slug) {
        return service.getPostBySlug(slug);
    }

    @PostMapping
    public BlogPostDTO createPost(@RequestBody BlogPostDTO dto) {
        return service.createPost(dto);
    }

    @PutMapping("/{id}")
    public BlogPostDTO updatePost(@PathVariable Integer id, @RequestBody BlogPostDTO dto) {
        return service.updatePost(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Integer id) {
        return service.deletePost(id) ? "Deleted successfully" : "Not found";
    }
}
