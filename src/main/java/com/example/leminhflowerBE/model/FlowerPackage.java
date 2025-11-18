package com.example.leminhflowerBE.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "flower_package")
public class FlowerPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer count;

    @ManyToMany
    @JoinTable(
            name = "flower_package_flowers",
            joinColumns = @JoinColumn(name = "flower_package_id"),
            inverseJoinColumns = @JoinColumn(name = "flower_id")
    )
    private List<Flower> flowers;
}
