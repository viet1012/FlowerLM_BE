package com.example.leminhflowerBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostDTO {
    private Integer id;
    private String title;
    private String slug;
    private String content;
    private String author;
    private String category;
    private String tags;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isPublished;


}
