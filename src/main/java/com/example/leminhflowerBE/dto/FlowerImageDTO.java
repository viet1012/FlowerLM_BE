package com.example.leminhflowerBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowerImageDTO {
    private Long imageId;
    private String imageUrl;
    private String imageType;
}
