package com.example.leminhflowerBE.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowerFeatureDTO {
    private Long id;
    private String title;
    private String description;
    private String iconUrl;
    private String colorTheme;
}
