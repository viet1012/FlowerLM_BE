package com.example.leminhflowerBE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "flowers")
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flowerId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnoreProperties("flowers") // tránh vòng lặp vô hạn khi serialize
    private FlowerGroup group;

    @Column(nullable = false)
    private String name;

    private String lifespan;
    private String origin;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String feature;

    @Column(columnDefinition = "TEXT")
    private String meaning;

    // 💰 Giá tiền
    private Double price;

    @OneToMany(mappedBy = "flower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlowerImage> images;
}
