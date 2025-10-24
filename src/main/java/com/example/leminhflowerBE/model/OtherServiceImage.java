package com.example.leminhflowerBE.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "other_service_image")
public class OtherServiceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    @JsonBackReference
    private OtherService service;

    @Column(length = 1000)
    private String imageUrl;
}
