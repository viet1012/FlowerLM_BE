package com.example.leminhflowerBE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlowerResponseDTO {
    private Long flowerId;
    private String name;
}
