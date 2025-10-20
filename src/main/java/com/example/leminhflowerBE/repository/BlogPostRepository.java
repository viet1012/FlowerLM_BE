package com.example.leminhflowerBE.repository;


import com.example.leminhflowerBE.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
    Optional<BlogPost> findBySlug(String slug);
}
