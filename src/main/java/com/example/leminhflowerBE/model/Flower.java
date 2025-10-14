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
    @JsonIgnoreProperties("group") // tránh vòng lặp
    private FlowerGroup group;
    private String name;
    private String lifespan;
    private String origin;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String feature;

    @Column(columnDefinition = "TEXT")
    private String meaning;

    @OneToMany(mappedBy = "flower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlowerImage> images;
}
