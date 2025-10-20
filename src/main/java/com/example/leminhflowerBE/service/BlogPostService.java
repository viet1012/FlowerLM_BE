package com.example.leminhflowerBE.service;


import com.example.leminhflowerBE.dto.BlogPostDTO;
import com.example.leminhflowerBE.model.BlogPost;
import com.example.leminhflowerBE.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository repository;

    private BlogPostDTO convertToDTO(BlogPost post) {
        BlogPostDTO dto = new BlogPostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setSlug(post.getSlug());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor());
        dto.setCategory(post.getCategory());
        dto.setTags(post.getTags());
        dto.setImageUrl(post.getImageUrl());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setIsPublished(post.getIsPublished());
        return dto;
    }

    private BlogPost convertToEntity(BlogPostDTO dto) {
        BlogPost post = new BlogPost();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setSlug(dto.getSlug());
        post.setContent(dto.getContent());
        post.setAuthor(dto.getAuthor());
        post.setCategory(dto.getCategory());
        post.setTags(dto.getTags());
        post.setImageUrl(dto.getImageUrl());
        post.setIsPublished(dto.getIsPublished());
        return post;
    }

    public List<BlogPostDTO> getAllPosts() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BlogPostDTO getPostById(Integer id) {
        return repository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public BlogPostDTO getPostBySlug(String slug) {
        return repository.findBySlug(slug).map(this::convertToDTO).orElse(null);
    }

    public BlogPostDTO createPost(BlogPostDTO dto) {
        BlogPost saved = repository.save(convertToEntity(dto));
        return convertToDTO(saved);
    }

    public BlogPostDTO updatePost(Integer id, BlogPostDTO dto) {
        Optional<BlogPost> existing = repository.findById(id);
        if (existing.isPresent()) {
            BlogPost post = existing.get();
            post.setTitle(dto.getTitle());
            post.setSlug(dto.getSlug());
            post.setContent(dto.getContent());
            post.setAuthor(dto.getAuthor());
            post.setCategory(dto.getCategory());
            post.setTags(dto.getTags());
            post.setImageUrl(dto.getImageUrl());
            post.setIsPublished(dto.getIsPublished());
            BlogPost updated = repository.save(post);
            return convertToDTO(updated);
        }
        return null;
    }

    public boolean deletePost(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
