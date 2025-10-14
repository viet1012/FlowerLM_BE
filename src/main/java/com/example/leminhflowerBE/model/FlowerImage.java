package com.example.leminhflowerBE.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "flower_images")
public class FlowerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "flower_id", nullable = false)
    private Flower flower;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    public enum ImageType {
        Anh1, Anh2
    }
}
