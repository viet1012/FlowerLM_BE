package com.example.leminhflowerBE.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flower_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowerFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon_url", length = 255)
    private String iconUrl;

    @Column(name = "color_theme", length = 50)
    private String colorTheme;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(nullable = false)
    private Boolean active = true;
}
